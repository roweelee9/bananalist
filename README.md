# BananaList

### An a-peel-ing and simple to-do list

*BananaList* is a simple to-do list app that aims to help users 
keep track of their important tasks to boost productivity. Anyone
can use *BananaList*, provided they have a working computer!

*BananaList* includes features such as:
* basic to-do list functionality
* urgency labels for tasks
* a point system to make you happy :)

### Why make BananaList?

A to-do list app is on the more practical/useful end of the list
of potential projects. The nerd part of me also thought that 
making a to-do list app would be fun and cool.

Also, with this being a course project, it comes with some criteria:

> For this project, you will NOT be allowed to:
> 1. Make any calls to external systems. All data you use must be inside your repos.

... and thus my other ideas (all of which require the use of some APIs) are dashed.

### User stories


* As a user, I want to be able to add a task to the to-do list
* As a user, I want to be able to delete a task from the to-do list
* As a user, I want to be able to mark/unmark a task as complete on the to-do list
* As a user, I want to be able to mark/unmark a task as urgent on the to-do list
* As a user, I want to be able to view tasks on the to-do list
* As a user, I want to be able to view number of incomplete and complete tasks on the to-do list
* As a user, I want to be able to filter completed tasks on the to-do list
* As a user, I want to be able to filter urgent tasks on the to-do list
* As a user, I want to be able to view number of points accumulated through completing tasks on the to-do list
* As a user, when I select the quit option from the application menu, I want the option to save my to-do list to file
* As a user, I want to be able to load my to-do list from file when the program starts

### Phase 4: Task 2
First option: Test and design a class that is robust - see Reader class in the persistence package.

### Phase 4: Task 3
Analysis:
* It is redundant that both the Reader and Writer classes have identical tasksPath and pointsPath 
fields for the same purpose - to reduce repetition of identical code, a JsonInteractor superclass 
was created to hold those fields.
* The Iterator Pattern can be applied to the ToDoList class such that design principles are not violated in
future updates to the app where one class may need to iterate through the ToDoList. (The current 
implementation of BananaListAppConsoleVersion.displayList() is built around using an ArrayList as an argument
such that it synergizes with ToDoList.getCompletedTasks() and ToDoList.getUrgentTasks(). Changing it to take
advantage of the new iterator would require a new similar method to display the completed and urgent tasks which
would violate design principles)
* There is a lack of cohesion in the Task class where there are helper methods that help print the
desired line in the overridden toString method - those could be combined into a single method to maximize
cohesion.