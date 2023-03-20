package nl.han.ica.icss.checker;

import nl.han.ica.Helpers.LiteralTypeVisitor;
import nl.han.ica.datastructures.HanLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;
import java.util.Objects;

public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HanLinkedList<>();
        variableTypes.addFirst(new HashMap<>());

        for (var astNode: ast.root.getChildren()) {
            if (astNode instanceof VariableAssignment) checkVariable((VariableAssignment) astNode);
            checkBody(astNode);
        }
    }

    int scope = 0;

    public void checkBody(ASTNode node) {
        if (hasBodyType(node)) {
            scope = scope + 1;
            HashMap<String, ExpressionType> scopeMap =  new HashMap<>();
            variableTypes.insert(scope, scopeMap);

            if (node instanceof IfClause) IfCheck((IfClause) node);

            for (var childNode: node.getChildren()) {
                checkNode(childNode);
            }

            variableTypes.delete(scope);
            scope = scope - 1;
        }
    }

    private void IfCheck(IfClause astNode) {
        var condition = astNode.conditionalExpression;
        if (getExpressionTypeOfKinds(condition, astNode) != ExpressionType.BOOL) astNode.setError("If must use bool!");
    }

    private boolean hasBodyType(ASTNode node) {
        return (node instanceof Stylerule || node instanceof IfClause || node instanceof ElseClause);
    }


    private void checkVariable(VariableAssignment astNode) {
        var ref = astNode.name;
        var expression = astNode.expression;

        if(expression instanceof Literal) {
            var type = determineLiteral((Literal) expression);
            variableTypes.get(scope).put(ref.name, type);
        } else if(expression instanceof VariableReference) {
            ExpressionType expressionType = checkRefVarExists((VariableReference) expression);
            variableTypes.get(scope).put(ref.name, expressionType);
        } else if(expression instanceof Operation) {
                ExpressionType expressionType = checkOpp((Operation) expression);
                variableTypes.get(scope).put(ref.name, expressionType);
        } else {
            astNode.setError("Variable reference has no valid body!");
        }
    }

    private ExpressionType checkRefVarExists(VariableReference varRef) {
        for(int i = 0; i <= scope; i++) {
            HashMap<String, ExpressionType> scopeLevelVariables = variableTypes.get(i);
            ExpressionType expressionType = scopeLevelVariables.get(varRef.name);
            var type = Objects.requireNonNullElse(expressionType, ExpressionType.UNDEFINED);
            if (type != ExpressionType.UNDEFINED) return type;
        }
        return ExpressionType.UNDEFINED;
    }

    private ExpressionType determineLiteral(Literal literal) {
        return literal.accept(new LiteralTypeVisitor());
    }

    private void checkNode(ASTNode astNode) {
        checkBody(astNode);
        if (astNode instanceof VariableAssignment) checkVariable((VariableAssignment) astNode);
        if (astNode instanceof Declaration) checkDecleration((Declaration) astNode);
    }

    private ExpressionType getExpressionTypeOfKinds(Expression type, ASTNode node) {
        ExpressionType expType = ExpressionType.UNDEFINED;
        if(type instanceof Literal) expType = determineLiteral((Literal) type);
        if(type instanceof Operation) expType = checkOpp((Operation) type);
        if(type instanceof VariableReference) {
            expType = checkRefVarExists((VariableReference) type);
            if (expType == ExpressionType.UNDEFINED) {
                node.setError("expression type not found for variable reference!");
            }
        }
        return expType;
    }


    private void checkDecleration(Declaration astNode) {
        var name = astNode.property.name;
        var type = astNode.expression;
        var expType = getExpressionTypeOfKinds(type, astNode);

        if ((name.equals("width") || name.equals("height")) && !(expType == ExpressionType.PIXEL || expType == ExpressionType.PERCENTAGE)) {
            astNode.setError(name + " must be of type Pixel or Percentage!");
        }

        if ((name.equals("color") || name.equals("background-color")) && !(expType == ExpressionType.COLOR)) {
            astNode.setError(name + " must be of type Color!");
        }
    }

    public ExpressionType checkOpp(Operation opp) {
        Expression lhs = opp.lhs;
        Expression rhs = opp.rhs;
        ExpressionType typeLhs = checkOppSide(lhs);
        ExpressionType typeRhs = checkOppSide(rhs);

        if(opp instanceof MultiplyOperation) {
            ifColorError(typeLhs, typeRhs, lhs, rhs);
            ifBoolError(typeLhs, typeRhs, lhs, rhs);
            if (!(typeLhs == ExpressionType.SCALAR) && !(typeRhs == ExpressionType.SCALAR)) {
                opp.setError("Multiply operation must contain at least one scalar literal");
            }
            return typeLhs == ExpressionType.SCALAR ? typeRhs : typeLhs;
        } else if(opp instanceof AddOperation || opp instanceof SubtractOperation) {
            ifColorError(typeLhs, typeRhs, lhs, rhs);
            ifBoolError(typeLhs, typeRhs, lhs, rhs);
            if (typeLhs != typeRhs) {
                opp.setError("Add and Subtract must contain same value type on both sides of the calculation.");
            }
            return typeLhs;
        } else {
            return ExpressionType.UNDEFINED;
        }
    }

    private void ifColorError(ExpressionType lhsType, ExpressionType rhsType, Expression lhs, Expression rhs) {
        if (lhsType == ExpressionType.COLOR) lhs.setError("Color not allowed!");
        if (rhsType == ExpressionType.COLOR) rhs.setError("Color not allowed!");
    }

    private void ifBoolError(ExpressionType lhsType, ExpressionType rhsType, Expression lhs, Expression rhs) {
        if (lhsType == ExpressionType.BOOL) lhs.setError("Bool not allowed!");
        if (rhsType == ExpressionType.BOOL) rhs.setError("Bool not allowed!");
    }

    private ExpressionType checkOppSide(Expression exp) {
        if (exp instanceof Operation) {
            return checkOpp((Operation) exp);
        }
        if (exp instanceof Literal) {
            return determineLiteral((Literal) exp);
        }
        return checkRefVarExists((VariableReference) exp);
    }
}
