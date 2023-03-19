package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;

public class Generator {

	StringBuilder sb = new StringBuilder();

	public String generate(AST ast) {
		for (var node: ast.root.getChildren()) {

			if (node instanceof Stylerule) {
				for (var childNode : node.getChildren()) {
					if (childNode instanceof Selector) sb.append(childNode.toString()).append(" {\n");
					if (childNode instanceof Declaration) {
						sb.append("\t");
						sb.append(((Declaration) childNode).property.name).append(": ");
						Expression expression = ((Declaration) childNode).expression;
						generateExpression(expression);
						sb.append(";\n");
					}
				}
				sb.append("}\n");
			}
		}
		return sb.toString();
	}

	private void generateExpression(Expression expression) {
		if(expression instanceof BoolLiteral) {
			sb.append(((BoolLiteral) expression).value);
		} else if(expression instanceof ColorLiteral) {
			sb.append(((ColorLiteral) expression).value);
		} else if(expression instanceof PercentageLiteral) {
			sb.append(((PercentageLiteral) expression).value).append("%");
		} else if(expression instanceof PixelLiteral) {
			sb.append(((PixelLiteral) expression).value).append("px");
		} else if(expression instanceof ScalarLiteral) {
			sb.append(((ScalarLiteral) expression).value);
		}
	}
}
