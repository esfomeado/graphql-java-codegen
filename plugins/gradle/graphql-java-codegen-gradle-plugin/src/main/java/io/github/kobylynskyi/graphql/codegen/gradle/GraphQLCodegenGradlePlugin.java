package io.github.kobylynskyi.graphql.codegen.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Gradle plugin for GraphQL code generation
 *
 * @author kobylynskyi
 */
public class GraphQLCodegenGradlePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        TaskContainer tasks = project.getTasks();
        tasks.create("graphqlCodegen", GraphQLCodegenGradleTask.class, task -> {
            task.setDefaultResourcesDir(findDefaultResourcesDir(project));
        });
        tasks.create("graphqlCodegenValidate", GraphQLCodegenValidateGradleTask.class);
    }

    private Optional<Path> findDefaultResourcesDir(Project project) {
        return project.getExtensions()
                .getByType(JavaPluginExtension.class)
                .getSourceSets()
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                .getResources()
                .getSourceDirectories()
                .getFiles()
                .stream()
                .findFirst()
                .map(File::toPath);
    }

}
