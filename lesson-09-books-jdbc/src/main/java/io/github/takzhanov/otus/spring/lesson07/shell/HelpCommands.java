package io.github.takzhanov.otus.spring.lesson07.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class HelpCommands {
    @ShellMethod(value = "Show help", key = {"h", "help"})
    public String showHelp() {
        return """
                Here are all the commands you can use:
                - List all authors: la or list-authors
                - Create a new author: ca or create-author --name "Author Name"
                - Update an author: ua or update-author --id 1 --name "Updated Author Name"
                - Delete an author: da or delete-author --id 1
                               
                - List all genres: lg or list-genres
                - Create a new genre: cg or create-genre --name "Genre Name"
                - Update a genre: ug or update-genre --id 1 --name "Updated Genre Name"
                - Delete a genre: dg or delete-genre --id 1
                               
                - List all books: l or lb or list
                - Create a new book: c or cb or create-book --title "Book Title" --genre "Genre Name" 
                --authors "Author Name 1,Author Name 2"
                - Update a book: ub or update-book --id 1 --title "Updated Book Title" --genre "Updated Genre Name" 
                --authors "Updated Author Name 1,Updated Author Name 2"
                - Patch a book: pb or patch-book --id 1 --title "Updated Book Title" --genre "Updated Genre Name" 
                --authors "Updated Author Name 1,Updated Author Name 2"
                - Delete a book: db or delete-book --id 1
                """;
    }
}


