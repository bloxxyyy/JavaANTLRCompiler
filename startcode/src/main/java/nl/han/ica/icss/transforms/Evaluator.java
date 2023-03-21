package nl.han.ica.icss.transforms;

import nl.han.ica.Helpers.IBodyable;
import nl.han.ica.Helpers.LiteralTypeVisitor;
import nl.han.ica.datastructures.HanLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.StackMap;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Evaluator implements Transform {

    private final HashMap<String, Literal> variableValues;

    public Evaluator() {
        variableValues = new HashMap<>();
    }

    @Override
    public void apply(AST ast) {
        checkNodes(ast.root);
    }

    private void checkNodes(ASTNode node) {
        for(ASTNode child : node.getChildren()) {
            if (child instanceof Stylerule) {
                checkNodes(child);
            }

            if (child instanceof VariableAssignment) {
                VariableAssignment n = (VariableAssignment) child;
                variableValues.put(n.name.name, getExpressionType(n.expression));
            }

            if (child instanceof Declaration) {
                ((Declaration) child).expression = getExpressionType(((Declaration) child).expression);
            }

            if (child instanceof IfClause) {
                checkNodes(child); // zorg dat de kindere in de if clause al gedaan zijn.
                var transform = transformForIfClause((IfClause) child);

                if (node instanceof IfClause) {
                    replaceOrAddForNode(node, child, transform);
                    ((IfClause) node).body.remove(child);
                    checkNodes(node);
                }

                if (node instanceof ElseClause) {
                    replaceOrAddForNode(node, child, transform);
                    ((ElseClause) node).body.remove(child);
                    checkNodes(node);
                }

                if (node instanceof Stylerule) {
                    replaceOrAddForNode(node, child, transform);
                    checkNodes(node);
                }
            }
        }
    }

    private void replaceOrAddForNode(ASTNode nodeType, ASTNode c, ArrayList<ASTNode> data) {
        ((IBodyable) nodeType).getBody().remove(c);
        var parentChildren = nodeType.getChildren();

        for (var child : data) {
            for (var pchild : parentChildren) {
                if (pchild instanceof VariableReference && child instanceof VariableReference) {
                    if (Objects.equals(((VariableReference) pchild).name, ((VariableReference) child).name)) {
                        ((IBodyable) nodeType).getBody().remove(pchild);
                        ((IBodyable) nodeType).getBody().add(child);
                        return;
                    }
                }
                if (pchild instanceof Declaration && child instanceof Declaration) {
                    if (Objects.equals(((Declaration) pchild).property.name, ((Declaration) child).property.name)) {
                        ((IBodyable) nodeType).getBody().remove(pchild);
                        ((IBodyable) nodeType).getBody().add(child);
                        return;
                    }
                }
            }
            ((IBodyable) nodeType).getBody().add(child);
        }
    }

    private ArrayList<ASTNode> transformForIfClause(IfClause ifClause) {
        if (conditionIsTrue(ifClause.conditionalExpression)) return ifClause.body;
        if(ifClause.elseClause != null) return ifClause.elseClause.body;
        return new ArrayList<>();
    }

    private boolean conditionIsTrue(Expression conditionalExpression) {
        if(conditionalExpression instanceof VariableReference) {
            return ((BoolLiteral) variableValues.get(((VariableReference) conditionalExpression).name)).value;
        }

        if(conditionalExpression instanceof BoolLiteral) return ((BoolLiteral) conditionalExpression).value;

        return false;
    }

    private Literal getExpressionType(Expression expression) {
        if(expression instanceof Literal) return (Literal) expression;
        if(expression instanceof VariableReference) return variableValues.get(((VariableReference) expression).name);
        if(expression instanceof Operation) return getOperationType((Operation) expression);
        return null;
    }

    private Literal getOperationType(Operation operation) {
        Literal lhs = getExpressionType(operation.lhs);
        Literal rhs = getExpressionType(operation.rhs);
        if(operation instanceof MultiplyOperation) return doOpp(lhs, rhs, OPP.MULTIPLY);
        if(operation instanceof AddOperation) return doOpp(lhs, rhs, OPP.ADD);
        if(operation instanceof SubtractOperation) return doOpp(lhs, rhs, OPP.SUBTRACT);
        return null;
    }

    enum OPP {ADD, SUBTRACT, MULTIPLY}
    private Literal doOpp(Literal lhs, Literal rhs, OPP operator) {
        int result;
        switch (operator) {
            case ADD:
                result = getInt(lhs) + getInt(rhs);
                break;
            case SUBTRACT:
                result = getInt(lhs) - getInt(rhs);
                break;
            case MULTIPLY:
                result = getInt(lhs) * getInt(rhs);
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        return createLiteral(result, lhs, rhs);
    }

    private int getInt(Literal literal) {
        var lit = literal.accept(new LiteralTypeVisitor());
        switch (lit) {
            case PERCENTAGE:
                return ((PercentageLiteral) literal).value;
            case PIXEL:
                return ((PixelLiteral) literal).value;
            case SCALAR:
                return ((ScalarLiteral) literal).value;
            default:
                throw new RuntimeException("Something went horribly wrong!");
        }
    }

    private Literal createLiteral(int result, Literal lhs, Literal rhs) {
        if (lhs instanceof ScalarLiteral) return createLiteralFromScalar(result, rhs);
        if (rhs instanceof ScalarLiteral) return createLiteralFromScalar(result, lhs);
        boolean bothPercentages = lhs instanceof PercentageLiteral && rhs instanceof PercentageLiteral;
        boolean bothPixels = lhs instanceof PixelLiteral && rhs instanceof PixelLiteral;
        if (bothPercentages) return new PercentageLiteral(result);
        if (bothPixels) return new PixelLiteral(result);
        return null;
    }

    private Literal createLiteralFromScalar(int result, Literal scalar) {
        boolean isPercentage = scalar instanceof PercentageLiteral;
        boolean isPixel = scalar instanceof PixelLiteral;
        if (isPercentage) return new PercentageLiteral(result);
        if (isPixel) return new PixelLiteral(result);
        return new ScalarLiteral(result);
    }
}
