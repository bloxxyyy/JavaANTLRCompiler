// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ICSSParser}.
 */
public interface ICSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ICSSParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(ICSSParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(ICSSParser.OperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#word}.
	 * @param ctx the parse tree
	 */
	void enterWord(ICSSParser.WordContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#word}.
	 * @param ctx the parse tree
	 */
	void exitWord(ICSSParser.WordContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#calculatable}.
	 * @param ctx the parse tree
	 */
	void enterCalculatable(ICSSParser.CalculatableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#calculatable}.
	 * @param ctx the parse tree
	 */
	void exitCalculatable(ICSSParser.CalculatableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#calc}.
	 * @param ctx the parse tree
	 */
	void enterCalc(ICSSParser.CalcContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#calc}.
	 * @param ctx the parse tree
	 */
	void exitCalc(ICSSParser.CalcContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ICSSParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ICSSParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(ICSSParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(ICSSParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#inLineVar}.
	 * @param ctx the parse tree
	 */
	void enterInLineVar(ICSSParser.InLineVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#inLineVar}.
	 * @param ctx the parse tree
	 */
	void exitInLineVar(ICSSParser.InLineVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(ICSSParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(ICSSParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(ICSSParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(ICSSParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#check}.
	 * @param ctx the parse tree
	 */
	void enterCheck(ICSSParser.CheckContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#check}.
	 * @param ctx the parse tree
	 */
	void exitCheck(ICSSParser.CheckContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ICSSParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ICSSParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void enterElseStatement(ICSSParser.ElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void exitElseStatement(ICSSParser.ElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#container}.
	 * @param ctx the parse tree
	 */
	void enterContainer(ICSSParser.ContainerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#container}.
	 * @param ctx the parse tree
	 */
	void exitContainer(ICSSParser.ContainerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#data}.
	 * @param ctx the parse tree
	 */
	void enterData(ICSSParser.DataContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#data}.
	 * @param ctx the parse tree
	 */
	void exitData(ICSSParser.DataContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(ICSSParser.StylesheetContext ctx);
}