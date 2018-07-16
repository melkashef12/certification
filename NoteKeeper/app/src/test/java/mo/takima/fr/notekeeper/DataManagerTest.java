package mo.takima.fr.notekeeper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataManagerTest {

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
}