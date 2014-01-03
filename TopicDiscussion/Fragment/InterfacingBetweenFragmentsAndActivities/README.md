This is readme file of `InterfacingBetweenFragmentsAndActivities` project.

As the parent project's README file says, I create a Interface with which Fragment can share event with host Activity.

Some changed part:

1. inner interface in fragment.
2. `onAtach()` in fragment should check whether the activity implement the interface or not.
3. use the interface to share the data.

More details can be found in code and comment.