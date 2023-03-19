package nl.han.ica.Helpers;

import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;

public class LiteralTypeVisitor implements LiteralVisitor<ExpressionType> {
    public ExpressionType visit(ColorLiteral literal) {
        return ExpressionType.COLOR;
    }

    public ExpressionType visit(PixelLiteral literal) {
        return ExpressionType.PIXEL;
    }

    public ExpressionType visit(PercentageLiteral literal) {
        return ExpressionType.PERCENTAGE;
    }

    public ExpressionType visit(ScalarLiteral literal) {
        return ExpressionType.SCALAR;
    }

    public ExpressionType visit(BoolLiteral literal) {
        return ExpressionType.BOOL;
    }
}
