package com.musta.belmo.entdto;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.musta.belmo.entdto.visitor.FieldQualifierVisitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class RemoveQualifierFromField {
	public static CompilationUnit removeQualifierFromField(File file) throws Exception {
		final CompilationUnit compilationUnit = JavaParser.parse(file);
		compilationUnit.accept(new FieldQualifierVisitor(), null);
		return compilationUnit;
	}
	
	
	public static void generateDTOSinDirectory(String srcDirectory) throws Exception {
		IOFileFilter fileFilter = new IOFileFilter() {
			@Override
			public boolean accept(File file) {
				return true;
			}
			
			@Override
			public boolean accept(File file, String s) {
				return true;
			}
		};
		Collection<File> files = FileUtils.listFiles(new File(srcDirectory), fileFilter, fileFilter);
		for (File sourceFile : files) {
			final CompilationUnit generatedDtoContent = removeQualifierFromField(sourceFile);
			FileUtils.write(sourceFile, generatedDtoContent.toString(), StandardCharsets.UTF_8);
		}
	}
}
