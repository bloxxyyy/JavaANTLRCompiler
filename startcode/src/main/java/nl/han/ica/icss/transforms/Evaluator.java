package nl.han.ica.icss.transforms;

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
        return null;
    }


}
