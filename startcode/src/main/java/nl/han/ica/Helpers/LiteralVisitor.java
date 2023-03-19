package nl.han.ica.Helpers;

import nl.han.ica.icss.ast.literals.*;

public interface LiteralVisitor<T> {
    T visit(ColorLiteral literal);

    T visit(PixelLiteral literal);

    T visit(PercentageLiteral literal);

    T visit(ScalarLiteral literal);

    T visit(BoolLiteral literal);
}
