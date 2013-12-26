This project consists of many sub-projects.


## part one

discuss

1. the lifecycle of activity and fragment (observe the LogCat)
2. dynamically add fragment to container 
2. using fragment's back stack (compare the two sub-projects in this part)

Sub-projects:

- [Without using BackStack](DynamicAddFragmentToContainerWithoutBackStack)
- [With using BackStack](DynamicAddFragmentToContainerWithBackStack)

## part two

The examples in part one have a problem that in `ArticleFragment#onTextChanged` method Fragment directly use parent Activity. It's not good.

The best practice is to use the Activity as an **intermediary**.This allows the Fragments to be as independent and **loosely coupled** as possible, with the responsibility for deciding how an event in one Fragment should affect the overall UI falling to the host Activity.

Where your Fragment needs to **share events** with its *host Activity* (such as signaling UI selections), it’s good practice to **create a callback interface within the Fragment** that a host Activity must implement.

Sub-projects:

- [Interfacing Between Fragments and Activities](InterfacingBetweenFragmentsAndActivities)

NOTE: Now you can see the **ToDoList2**. It use above knowledge to refactor the first version.


## TODO Topic

- Fragment without ID.
- how to use bundle in fragment