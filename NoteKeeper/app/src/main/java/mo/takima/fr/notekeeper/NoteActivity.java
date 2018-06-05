package mo.takima.fr.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

  public static final String NOTE_POSITION = "mo.takima.fr.NOTE_POSITION";
  public static final int POSITION_NOT_SET = -1;

  private NoteInfo mNote;
  private boolean mIsNewNote;
  private Spinner mSpinner;
  private EditText mTextNoteTitle;
  private EditText mTextNoteText;
  private int mNotePosition;
  private boolean mIsCanceling;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_note);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mSpinner = findViewById(R.id.spinner_courses);
    List<CourseInfo> courses = DataManager.getInstance().getCourses();

    ArrayAdapter<CourseInfo> adapterCourses = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,courses);
    adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mSpinner.setAdapter(adapterCourses);

    readDisplayStateValues();

    mTextNoteTitle = findViewById(R.id.text_note_title);
    mTextNoteText = findViewById(R.id.text_note_text);

    if(!mIsNewNote){
      displaySelectedNote(mSpinner, mTextNoteTitle, mTextNoteText);
    }
  }


  private void readDisplayStateValues() {
    Intent intent = getIntent();
    int position = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
    mIsNewNote = position == POSITION_NOT_SET;
    if(mIsNewNote) {
      createNewNote();
    } else {
      mNote = DataManager.getInstance().getNotes().get(position);
    }
  }

  private void displaySelectedNote(Spinner spinner, EditText textNoteTitle, EditText textNoteText) {
    selectNoteInSpinner(spinner);
    textNoteTitle.setText(mNote.getTitle());
    textNoteText.setText(mNote.getText());
  }

  private void selectNoteInSpinner(Spinner spinner) {
    List<CourseInfo> courses = DataManager.getInstance().getCourses();
    int index = courses.indexOf(mNote.getCourse());
    spinner.setSelection(index);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_note, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_send_mail) {
      sendEmail();
      return true;
    } else if (id == R.id.action_cancel){
      mIsCanceling = true;
      finish();
    }

    return super.onOptionsItemSelected(item);
  }

  @Override protected void onPause() {
    super.onPause();
    if(mIsCanceling){
       if(mIsNewNote){
        DataManager.getInstance().removeNote(mNotePosition);
      }
    } else {
        saveNote();
    }
  }

  private void sendEmail() {
      CourseInfo course = (CourseInfo) mSpinner.getSelectedItem();
      String subject = mTextNoteTitle.getText().toString();
      String body = "Checkout what I learned in the Pluralsight course \""+
          course.getTitle()+"\"\n" + mTextNoteText.getText().toString();

      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setType("message/rfc2822");
      intent.putExtra(Intent.EXTRA_SUBJECT,subject);
      intent.putExtra(Intent.EXTRA_TEXT,body);
      startActivity(intent);
  }

  private void saveNote() {
      mNote.setCourse((CourseInfo) mSpinner.getSelectedItem());
      mNote.setTitle(mTextNoteTitle.getText().toString());
      mNote.setText(mTextNoteText.getText().toString());
  }


  private void createNewNote() {
    DataManager dm = DataManager.getInstance();
    mNotePosition = dm.createNewNote();
    mNote = dm.getNotes().get(mNotePosition);
  }
}
