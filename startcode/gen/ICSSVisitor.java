// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ICSSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ICSSVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ICSSParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(ICSSParser.OperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#word}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWord(ICSSParser.WordContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#calculatable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalculatable(ICSSParser.CalculatableContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#calc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalc(ICSSParser.CalcContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(ICSSParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(ICSSParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#inLineVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInLineVar(ICSSParser.InLineVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(ICSSParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(ICSSParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#check}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCheck(ICSSParser.CheckContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ICSSParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#elseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseStatement(ICSSParser.ElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#container}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContainer(ICSSParser.ContainerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#data}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitData(ICSSParser.DataContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(ICSSParser.StylesheetContext ctx);
}