package mo.takima.fr.notekeeper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataManagerTest {

  private static DataManager sDataManager;

  @BeforeClass
  public static void classSetup(){
    sDataManager = DataManager.getInstance();
  }

  @Before
  public void setUp()  {
    sDataManager.getNotes().clear();
    sDataManager.initializeExampleNotes();
  }

  @Test public void createNewNote() {
    final CourseInfo courseInfo = sDataManager.getCourse("android_async");
    final String noteTitle = "Test note title";
    final String noteText = "This is the body text of my test note";

    int noteIndex = sDataManager.createNewNote();
    NoteInfo noteInfo = sDataManager.getNotes().get(noteIndex);
    noteInfo.setCourse(courseInfo);
    noteInfo.setTitle(noteTitle);
    noteInfo.setText(noteText);

    NoteInfo compareNote = sDataManager.getNotes().get(noteIndex);
    assertEquals(courseInfo,compareNote.getCourse());
    assertEquals(noteTitle,compareNote.getTitle());
    assertEquals(noteText,compareNote.getText());
  }

  @Test public void findSimilarNotes() {
    final CourseInfo courseInfo = sDataManager.getCourse("android_async");
    final String noteTitle = "Test note title";
    final String noteText1 = "This is the body text of my test note";
    final String noteText2 = "This is the body of my second test note";

    int noteIndex1 = sDataManager.createNewNote();
    NoteInfo noteInfo1 = sDataManager.getNotes().get(noteIndex1);
    noteInfo1.setCourse(courseInfo);
    noteInfo1.setTitle(noteTitle);
    noteInfo1.setText(noteText1);

    int noteIndex2 = sDataManager.createNewNote();
    NoteInfo noteInfo2 = sDataManager.getNotes().get(noteIndex2);
    noteInfo2.setCourse(courseInfo);
    noteInfo2.setTitle(noteTitle);
    noteInfo2.setText(noteText2);

    int foundIndex1 = sDataManager.findNote(noteInfo1);
    assertEquals(noteIndex1,foundIndex1);

    int foundIndex2 = sDataManager.findNote(noteInfo2);
    assertEquals(noteIndex2,foundIndex2);

  }

  @Test public void createNewNoteOneStepCreation() {
    final CourseInfo courseInfo = sDataManager.getCourse("android_async");
    final String noteTitle = "Test note title";
    final String noteText = "This is the body text of my test note";

    int noteIndex = sDataManager.createNewNote(courseInfo,noteTitle,noteText);

    NoteInfo compareNote = sDataManager.getNotes().get(noteIndex);
    assertEquals(courseInfo,compareNote.getCourse());
    assertEquals(noteTitle,compareNote.getTitle());
    assertEquals(noteText,compareNote.getText());

  }
}