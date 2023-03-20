package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.datastructures.StackMap;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

import java.util.Objects;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new StackMap<>();
	}

	@Override public void enterIfstmt(ICSSParser.IfstmtContext ctx) {
		IfClause i = new IfClause();
		currentContainer.peek().addChild(i);
		currentContainer.push(i);
	}
	@Override public void exitIfstmt(ICSSParser.IfstmtContext ctx) {
		currentContainer.pop();
	}
	@Override public void enterElsestmt(ICSSParser.ElsestmtContext ctx) {
		ElseClause e = new ElseClause();
		currentContainer.peek().addChild(e);
		currentContainer.push(e);
	}
	@Override public void exitElsestmt(ICSSParser.ElsestmtContext ctx) {
		currentContainer.pop();
	}

	@Override public void enterExp(ICSSParser.ExpContext ctx) {
		Operation exp = null;
		if (ctx.children.size() < 2) return;
		String name = ctx.getChild(1).getText();

		if (Objects.equals(name, "*")) exp = new MultiplyOperation();
		if (Objects.equals(name, "-")) exp = new SubtractOperation();
		if (Objects.equals(name, "+")) exp = new AddOperation();

		currentContainer.peek().addChild(exp);
		currentContainer.push(exp);
	}

	@Override
	public void exitExp(ICSSParser.ExpContext ctx) {
		if (ctx.children.size() < 2) return;
		currentContainer.pop();
	}

	@Override public void enterVari(ICSSParser.VariContext ctx) {
		VariableAssignment assign = new VariableAssignment();
		currentContainer.peek().addChild(assign);
		currentContainer.push(assign);
	}

	@Override public void exitVari(ICSSParser.VariContext ctx) {
		currentContainer.pop();
	}

	@Override public void exitVariablereference(ICSSParser.VariablereferenceContext ctx) {
		VariableReference varRef = new VariableReference(ctx.getText());
		currentContainer.peek().addChild(varRef);
	}


	@Override
	public void exitIdSelect(ICSSParser.IdSelectContext ctx) {
		IdSelector idSelector = new IdSelector(ctx.getText());
		currentContainer.peek().addChild(idSelector);
	}

	@Override
	public void exitClassSelect(ICSSParser.ClassSelectContext ctx) {
		ClassSelector classSelector = new ClassSelector(ctx.getText());
		currentContainer.peek().addChild(classSelector);
	}

	@Override
	public void exitProperty(ICSSParser.PropertyContext ctx) {
		PropertyName propertyName = new PropertyName(ctx.getText());
		currentContainer.peek().addChild(propertyName);
	}

	@Override
	public void enterSheetrule(ICSSParser.SheetruleContext ctx) {
		Stylerule stylerule = new Stylerule();
		currentContainer.peek().addChild(stylerule);
		currentContainer.push(stylerule);
	}

	@Override
	public void exitSheetrule(ICSSParser.SheetruleContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void exitLiteral(ICSSParser.LiteralContext ctx) {
		var literal = checkLiteral(ctx.getText());
		currentContainer.peek().addChild(literal);
	}

	private Literal checkLiteral(String text){
		if (text.contains("px")) return new PixelLiteral(text);
		if (text.contains("%")) return new PercentageLiteral(text);
		if (text.contains("#")) return new ColorLiteral(text);
		if (Objects.equals(text, "TRUE") || Objects.equals(text, "FALSE")) return new BoolLiteral(text);
		return new ScalarLiteral(text);
	}

	@Override
	public void enterDecl(ICSSParser.DeclContext ctx) {
		Declaration declaration = new Declaration();
		currentContainer.peek().addChild(declaration);
		currentContainer.push(declaration);
	}

	@Override
	public void exitDecl(ICSSParser.DeclContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void exitTagSelect(ICSSParser.TagSelectContext ctx) {
		TagSelector tagSelector = new TagSelector(ctx.getText());
		currentContainer.peek().addChild(tagSelector);
	}

	@Override public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet stylesheet = new Stylesheet();
		ast.setRoot(stylesheet);
		currentContainer.push(stylesheet);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		currentContainer.pop();
	}

	public AST getAST() {
        return ast;
    }
    
}