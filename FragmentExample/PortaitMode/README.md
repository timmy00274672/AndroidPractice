This is read me for *PortaitMode* project.

This project show one method to display different UI based on different configuration(here, orientation).

In this project, UI is different for portait mode or other orientation mode.

## portait mode

`setContentView(R.layout.activity_rssfeed);` in `RssfeedActivity` will inflate the `activity_rssfeed.xml` in **layout-port** instead of *layout*.

And after infattion, `MyListFragment` will be displayed.  When button pressed, the `RssfeedActivity#onRssItemSelected` is called, and into *else block* and display the output in anther `DetailActivity`.

## Other mode

`setContentView(R.layout.activity_rssfeed);` in `RssfeedActivity` will inflate the `activity_rssfeed.xml` in **layout**, with two fragments.

When the button pressed, the `RssfeedActivity#onRssItemSelected` is called, and into *if true block*, and display the output in the fragment that is already in the layout.


## Discussion: How to work with Fragments

To create different layouts with Fragments you can:

- Use one activity, which displays two Fragments for tablets and only one on handsets devices. In this case you would switch the Fragments in the activity whenever necessary. This requires that the fragment is not declared in the layout file as such Fragments cannot be removed during runtime. It also requires an update of the action bar if the action bar status depends on the fragment.

- Use separate activities to host each fragment on a handset. For example, when the tablet UI uses two Fragments in an activity, use the same activity for handsets, but supply an alternative layout that includes just one fragment. When you need to switch Fragments, start another activity that hosts the other fragment.

The **second** approach is the **most flexible** and in general preferable way of using Fragments. In this case the main activity checks if the detail fragment is available in the layout. If the detailed fragment is there, the main activity tells the fragment that it should update itself. If the detail fragment is not available, the main activity starts the detailed activity. 