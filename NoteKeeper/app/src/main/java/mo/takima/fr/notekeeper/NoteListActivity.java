package mo.takima.fr.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

import static mo.takima.fr.notekeeper.NoteActivity.NOTE_INFO;

public class NoteListActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_note_list);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
      }
    });


    initializeDisplayContent();
  }

  private void initializeDisplayContent() {
    final ListView listNotes = findViewById(R.id.list_notes);

    List<NoteInfo> notes = DataManager.getInstance().getNotes();

    ArrayAdapter<NoteInfo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,notes );
    listNotes.setAdapter(adapter);

    listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);

        NoteInfo noteInfo = (NoteInfo) listNotes.getItemAtPosition(position);
        intent.putExtra(NOTE_INFO,noteInfo);

        startActivity(intent);
      }
    });
  }
}
