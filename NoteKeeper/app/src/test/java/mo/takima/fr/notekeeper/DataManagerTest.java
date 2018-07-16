package mo.takima.fr.notekeeper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataManagerTest {

  @Before
  public void setUp() throws Exception {
    DataManager dm = DataManager.getInstance();
    dm.getNotes().clear();
    dm.initializeExampleNotes();
  }

  @Test public void createNewNote() {
    DataManager dm = DataManager.getInstance();
    final CourseInfo courseInfo = dm.getCourse("android_async");
    final String noteTitle = "Test note title";
    final String noteText = "This is the body text of my test note";

    int noteIndex = dm.createNewNote();
    NoteInfo noteInfo = dm.getNotes().get(noteIndex);
    noteInfo.setCourse(courseInfo);
    noteInfo.setTitle(noteTitle);
    noteInfo.setText(noteText);

    NoteInfo compareNote = dm.getNotes().get(noteIndex);
    assertEquals(courseInfo,compareNote.getCourse());
    assertEquals(noteTitle,compareNote.getTitle());
    assertEquals(noteText,compareNote.getText());
  }

  @Test public void findSimilarNotes() {
    DataManager dm = DataManager.getInstance();
    final CourseInfo courseInfo = dm.getCourse("android_async");
    final String noteTitle = "Test note title";
    final String noteText1 = "This is the body text of my test note";
    final String noteText2 = "This is the body of my second test note";

    int noteIndex1 = dm.createNewNote();
    NoteInfo noteInfo1 = dm.getNotes().get(noteIndex1);
    noteInfo1.setCourse(courseInfo);
    noteInfo1.setTitle(noteTitle);
    noteInfo1.setText(noteText1);

    int noteIndex2 = dm.createNewNote();
    NoteInfo noteInfo2 = dm.getNotes().get(noteIndex2);
    noteInfo2.setCourse(courseInfo);
    noteInfo2.setTitle(noteTitle);
    noteInfo2.setText(noteText2);

    int foundIndex1 = dm.findNote(noteInfo1);
    assertEquals(noteIndex1,foundIndex1);

    int foundIndex2 = dm.findNote(noteInfo2);
    assertEquals(noteIndex2,foundIndex2);

  }
}