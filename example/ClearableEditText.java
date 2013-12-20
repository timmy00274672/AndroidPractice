public class ClearableEditText extends LinearLayout {
	EditText editText;
	Button clearButton;
	
	public ClearableEditText(Context context) {
		super(context);
		
		// Inflate the view from the layout resource.
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.clearable_edit_text, this, true);
		
		// Get references to the child controls.
		editText = (EditText)findViewById(R.id.editText);
		clearButton = (Button)findViewById(R.id.clearButton);
		
		// Hook up the functionality
		hookupButton();
	}
	
	private void hookupButton() {
	clearButton.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			editText.setText("");
		}});
	}
}