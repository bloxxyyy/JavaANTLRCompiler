grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';

//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;

//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

COL: 'color';
BACKG: 'background-color';
WIDTH: 'width';
HEIGHT: 'height';


//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
DEV: '/';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';

//--- PARSER: ---

stylesheet: (vari)* (sheetrule)* EOF;

vari: variablereference ASSIGNMENT_OPERATOR (literal | exp) SEMICOLON;
variablereference: CAPITAL_IDENT;

sheetrule: selec OPEN_BRACE (ifstmt | decl | vari)* CLOSE_BRACE;

calc: (literal | variablereference) ((MUL | PLUS | MIN) (literal | variablereference | calc));

selec: (idSelect | classSelect | tagSelect);
idSelect: ID_IDENT;
classSelect: CLASS_IDENT;
tagSelect: LOWER_IDENT;

decl: property COLON (literal | variablereference | exp) SEMICOLON;
property: (COL | BACKG | WIDTH | HEIGHT)+;

literal: COLOR | bool | PERCENTAGE | SCALAR| PIXELSIZE;
bool: TRUE | FALSE;

exp: exp PLUS exp | exp MUL exp | exp MIN exp | (variablereference | literal);

ifstmt: IF BOX_BRACKET_OPEN (variablereference | bool) BOX_BRACKET_CLOSE OPEN_BRACE (ifstmt | decl | vari)* CLOSE_BRACE (elsestmt)?;
elsestmt: ELSE OPEN_BRACE (ifstmt | decl | vari)* CLOSE_BRACE;
