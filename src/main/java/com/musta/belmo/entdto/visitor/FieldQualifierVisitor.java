package com.musta.belmo.entdto.visitor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FieldQualifierVisitor extends VoidVisitorAdapter<CompilationUnit> {
	
	@Override
	public void visit(FieldDeclaration field, CompilationUnit destination) {
		if(field.isAnnotationPresent("Qualifier")){
			field.getAnnotationByName("Qualifier").ifPresent(Node::remove);
		}
		super.visit(field, destination);
	}
}
