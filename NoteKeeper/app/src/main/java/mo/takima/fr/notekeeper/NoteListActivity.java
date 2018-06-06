package mo.takima.fr.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

import static mo.takima.fr.notekeeper.NoteActivity.NOTE_POSITION;

public class NoteListActivity extends AppCompatActivity {

  private ArrayAdapter<NoteInfo> mAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_note_list);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
      }
    });


    initializeDisplayContent();
  }

  @Override protected void onResume() {
    super.onResume();
    mAdapter.notifyDataSetChanged();
  }

  private void initializeDisplayContent() {
    final ListView listNotes = findViewById(R.id.list_notes);

    List<NoteInfo> notes = DataManager.getInstance().getNotes();

    mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,notes );
    listNotes.setAdapter(mAdapter);

    listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);

        //NoteInfo noteInfo = (NoteInfo) listNotes.getItemAtPosition(position);
        intent.putExtra(NOTE_POSITION,position);

        startActivity(intent);
      }
    });
  }
}
