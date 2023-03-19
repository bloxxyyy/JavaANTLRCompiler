package nl.han.ica.icss.ast;

import nl.han.ica.Helpers.LiteralVisitor;

public abstract class Literal extends Expression {
    public abstract <T> T accept(LiteralVisitor<T> visitor);
}

