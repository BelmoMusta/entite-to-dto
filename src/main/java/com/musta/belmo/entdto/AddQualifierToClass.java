package com.musta.belmo.entdto;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.musta.belmo.entdto.visitor.AddComponenetClassVisitor;
import com.musta.belmo.entdto.visitor.RemoveComponentClassVisitor;
import com.musta.belmo.entdto.visitor.RemoveQualifierClassVisitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class AddQualifierToClass {
	
	
	public static CompilationUnit addQualifier(File file) throws Exception {
		final CompilationUnit compilationUnit = JavaParser.parse(file);
		
		final ClassOrInterfaceDeclaration aClass = compilationUnit.accept(new AddComponenetClassVisitor(),
				compilationUnit);
		new AddComponenetClassVisitor();
		if (aClass == null) {
			return null;
		}
		return compilationUnit;
	}
	
	public static CompilationUnit removeQualifier(File file) throws Exception {
		final CompilationUnit compilationUnit = JavaParser.parse(file);
		
		final ClassOrInterfaceDeclaration aClass = compilationUnit.accept(new RemoveQualifierClassVisitor(),
				compilationUnit);
		new AddComponenetClassVisitor();
		if (aClass == null) {
			return null;
		}
		return compilationUnit;
	}
	
	public static CompilationUnit removeComponent(File file) throws Exception {
		final CompilationUnit compilationUnit = JavaParser.parse(file);
		
		final ClassOrInterfaceDeclaration aClass = compilationUnit.accept(new RemoveComponentClassVisitor(),
				compilationUnit);
		new AddComponenetClassVisitor();
		if (aClass == null) {
			return null;
		}
		return compilationUnit;
	}
	
	public static void generateDTOSinDirectory(String srcDirectory) throws Exception {
		IOFileFilter fileFilter = new IOFileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getName().endsWith("QuestionConditionImpl.java");
			}
			
			@Override
			public boolean accept(File file, String s) {
				return true;
			}
		};
		Collection<File> files = FileUtils.listFiles(new File(srcDirectory), fileFilter, fileFilter);
		for (File sourceFile : files) {
			final CompilationUnit generatedDtoContent = addQualifier(sourceFile);
			if (generatedDtoContent == null) {
				continue;
			}
			FileUtils.write(sourceFile, generatedDtoContent.toString(), StandardCharsets.UTF_8);
		}
	}
	
}
