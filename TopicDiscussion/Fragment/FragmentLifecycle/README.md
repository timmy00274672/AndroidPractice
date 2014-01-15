This is a note for `FragmentLifecycle`.

## Preface

I research the lifecycle between `Activity` and `Fragment`s for a terrible problem happens in another project because I'm not familiar with. I describe that below:

In that application, portrait and landscape mode are with different layout. Portrait mode has *Fragment A(FA)*, and landscape has *Fragment B(FB)*, which I dynamically add to the UI. In *FB*, `onCreateView` use `getArguments` to initailize the field, which is `File` object. 

When I rotate from the landscape mode into portrait mode, problem occurs in *FB*'s `onCreateView`. It arise some doubts:

- Why there are `NullPointerException`, I make sure that initalization are correct, because I use the Builder of FB. The FB builded from the Builder does not have null field. 
- In portrait mode, there is no FB. Why FB is created. (In my codes, I make sure dynamically add FB only as landscape mode.)

To solve this question, I reseach lifecycle issue with this project.

## Answer

I realize :

- Fragments are recreated as the configuration changed. 

	It can be observe through the `hashCode()`. And the fragment will be attached if it was attached before. 
- You should not remove the Fragment in `Activity#onPause`, `Activity#onStop`, `Activity#onDestory`.
- Based on the above two points, you should make sure the use of `onSavedInstanceState` or `getArguments` will not casue `NullPointerException` when you initalize any thing.
- You should always notice the *if the getter return null* problem.
- I suggest you not save the visible Fragment as a Field, because it may be **asynchronous**. You can use an getter that find the fragment everytime, or you should make sure everytime you do a transaction with it with synchronous state. For example, you should remove the fragment and also set the field `null`.
- Always deal with the `null` case when you use `Bundle`.