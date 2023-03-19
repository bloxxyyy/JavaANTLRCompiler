package nl.han.ica.icss.checker;

import nl.han.ica.Helpers.LiteralTypeVisitor;
import nl.han.ica.datastructures.HanLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;
import java.util.Objects;

public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HanLinkedList<>();
        variableTypes.addFirst(new HashMap<>());

        for (var astNode: ast.root.getChildren()) {

            if (astNode instanceof VariableAssignment) {
                checkVariable((VariableAssignment) astNode);
            }
            if (astNode instanceof Stylerule) {
                for (var childNode: astNode.getChildren()) {
                    checkNode(childNode);
                }
            }
        }
    }

    private void checkVariable(VariableAssignment astNode) {
        var ref = astNode.name;
        var expression = astNode.expression;

        if(expression instanceof Literal) {
            var type = determineLiteral((Literal) expression);
            variableTypes.get(0).put(ref.name, type);
        } else if(expression instanceof VariableReference) {
            ExpressionType expressionType = checkRefVarExists((VariableReference) expression);
            variableTypes.get(0).put(ref.name, expressionType);
        } else {
            astNode.setError("Variable reference has no valid body!");
        }
    }

    private ExpressionType checkRefVarExists(VariableReference varRef) {
        //for(int i = 0; i <= scopeLevel; i++) {
        HashMap<String, ExpressionType> scopeLevelVariables = variableTypes.get(0);
        ExpressionType expressionType = scopeLevelVariables.get(varRef.name);
        return Objects.requireNonNullElse(expressionType, ExpressionType.UNDEFINED);
       // }
    }

    private ExpressionType determineLiteral(Literal literal) {
        return literal.accept(new LiteralTypeVisitor());
    }

    private void checkNode(ASTNode astNode) {
        if (astNode instanceof Declaration) {
            checkDecleration((Declaration) astNode);
        }
    }

    private void checkDecleration(Declaration astNode) {
        var name = astNode.property.name;
        var type = astNode.expression;
        var expType = ExpressionType.UNDEFINED;

        if(type instanceof Literal) {
            expType = determineLiteral((Literal) type);
        }

        if(type instanceof VariableReference) {
            expType = checkRefVarExists((VariableReference) type);
            if (expType == ExpressionType.UNDEFINED) {
                astNode.setError(name + " expression type not found for variable reference!");
            }
        }

        if ((name.equals("width") || name.equals("height")) && !(expType == ExpressionType.PIXEL || expType == ExpressionType.PERCENTAGE)) {
            astNode.setError(name + " must be of type Pixel or Percentage!");
        }

        if ((name.equals("color") || name.equals("background-color")) && !(expType == ExpressionType.COLOR)) {
            astNode.setError(name + " must be of type Color!");
        }
    }
}
