/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import com.okgabe.mastr2.command.CommandBase;
import org.reflections.Reflections;

import java.util.Set;

public class ReflectionUtil {
//    /**
//     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
//     *
//     * @param packageName The base package
//     * @return The classes
//     * @throws ClassNotFoundException
//     * @throws IOException
//     *
//     * From https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
//     */
//    public static Class[] getClasses(String packageName)
//            throws ClassNotFoundException, IOException {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        assert classLoader != null;
//        String path = packageName.replace('.', '/');
//        Enumeration<URL> resources = classLoader.getResources(path);
//        List<File> dirs = new ArrayList<>();
//        while (resources.hasMoreElements()) {
//            URL resource = resources.nextElement();
//            dirs.add(new File(resource.getFile()));
//        }
//        ArrayList<Class> classes = new ArrayList<>();
//        for (File directory : dirs) {
//            classes.addAll(findClasses(directory, packageName));
//        }
//        return classes.toArray(new Class[classes.size()]);
//    }
//
//    /**
//     * Recursive method used to find all classes in a given directory and subdirs.
//     *
//     * @param directory   The base directory
//     * @param packageName The package name for classes found inside the base directory
//     * @return The classes
//     * @throws ClassNotFoundException
//     *
//     * From https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
//     */
//    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
//        List<Class> classes = new ArrayList<>();
//        if (!directory.exists()) {
//            return classes;
//        }
//        File[] files = directory.listFiles();
//        for (File file : files) {
//            if (file.isDirectory()) {
//                assert !file.getName().contains(".");
//                classes.addAll(findClasses(file, packageName + "." + file.getName()));
//            } else if (file.getName().endsWith(".class")) {
//                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
//            }
//        }
//        return classes;
//    }

    // From https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
    // Due to incompatibility issues with Java 11, the Reflections library had to be downgraded from 0.9.12 to 0.9.11. Check in with
    // this library in the future to see if a newer version is out which fixes this issue.
    public static Set<Class<? extends CommandBase>> getClasses(String packageName){
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(CommandBase.class);
    }
}
