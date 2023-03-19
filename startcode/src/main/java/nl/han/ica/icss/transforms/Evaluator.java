package nl.han.ica.icss.transforms;

import nl.han.ica.Helpers.LiteralTypeVisitor;
import nl.han.ica.datastructures.HanLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.StackMap;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private HashMap<String, Literal> variableValues;

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
        }
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
