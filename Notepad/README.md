
## Requirement

function:

- multi-tabs
- auto save
- save, open, new
- content prsovider
- preference of size and font
- auto restore state
- search text
- double click tab to close
- slide between tabs
- night/normal/custom mode

non-function:

- at least one tab opened
- at most four tabs opened
 
## Feasible Analysis

###topic
1. listener of list view
2. slide between tabs
3. color picker
4. widget in preference page
5. use cache or not
6. lifecycle issue (Fragment and Activity)
7. Content provider
8. store state
9. highlight text(with `EditText`??)
10. listener for closing keyboard event
11. listener for timer

### feasible test stage 1 plan:

- fragment for edit view
- action bar and menu
	- save
	- action bar show the file names
		- length limit 
- auto save
	- close app
- auto restore : consider flexibility
	- use `bundle`
- content providle
