package com.common.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class PackageScannerUtils {
	public static List<Class<?>> scan(String packageName)
			throws ClassNotFoundException {
		LinkedList classes = new LinkedList();

		try {
			ClassLoader e = Thread.currentThread().getContextClassLoader();
			Enumeration urls = e.getResources(packageName.replace('.', '/'));

			while (urls.hasMoreElements()) {
				URI uri = ((URL) urls.nextElement()).toURI();
				String arg4 = uri.getScheme().toLowerCase();
				byte arg5 = -1;
				switch (arg4.hashCode()) {
					case 104987 :
						if (arg4.equals("jar")) {
							arg5 = 0;
						}
						break;
					case 3143036 :
						if (arg4.equals("file")) {
							arg5 = 1;
						}
				}

				switch (arg5) {
					case 0 :
						scanFromJarProtocol(e, classes,
								uri.getRawSchemeSpecificPart());
						break;
					case 1 :
						scanFromFileProtocol(e, classes, uri.getPath(),
								packageName);
						break;
					default :
						throw new URISyntaxException(uri.getScheme(),
								"unknown schema " + uri.getScheme());
				}
			}
		} catch (IOException | URISyntaxException arg6) {
			arg6.printStackTrace();
		}

		return classes;
	}

	private static Class<?> loadClass(ClassLoader loader, String classPath)
			throws ClassNotFoundException {
		classPath = classPath.substring(0, classPath.length() - 6);
		return loader.loadClass(classPath);
	}

	private static void scanFromFileProtocol(ClassLoader loader,
			List<Class<?>> classes, String dir, String packageName)
			throws ClassNotFoundException {
		File directory = new File(dir);
		File[] files = directory.listFiles();
		if (directory.isDirectory() && files != null) {
			File[] arg5 = files;
			int arg6 = files.length;

			for (int arg7 = 0; arg7 < arg6; ++arg7) {
				File classFile = arg5[arg7];
				if (!classFile.isDirectory()
						&& classFile.getName().endsWith(".class")
						&& !classFile.getName().contains("$")) {
					String className = String.format("%s.%s", new Object[]{
							packageName, classFile.getName()});
					classes.add(loadClass(loader, className));
				}
			}
		}

	}

	private static void scanFromJarProtocol(ClassLoader loader,
			List<Class<?>> classes, String fullPath)
			throws ClassNotFoundException {
		String jar = fullPath.substring(0, fullPath.lastIndexOf(33));
		String parent = fullPath.substring(fullPath.lastIndexOf(33) + 2);
		JarEntry e = null;
		JarInputStream jarReader = null;

		try {
			for (jarReader = new JarInputStream((new URL(jar)).openStream()); (e = jarReader
					.getNextJarEntry()) != null; jarReader.closeEntry()) {
				String exp = e.getName();
				if (!e.isDirectory() && exp.startsWith(parent)
						&& exp.endsWith(".class") && !exp.contains("$")) {
					exp = exp.replace('/', '.');
					classes.add(loadClass(loader, exp));
				}
			}
		} catch (IOException arg15) {
			arg15.printStackTrace();
		} finally {
			try {
				if (jarReader != null) {
					jarReader.close();
				}
			} catch (IOException arg14) {
				arg14.printStackTrace();
			}

		}

	}
}