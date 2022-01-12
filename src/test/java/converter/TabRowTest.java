package converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import utility.AnchoredText;
import utility.Settings;
import utility.ValidationError;

public class TabRowTest {
    public TabRow tabRowInstance;

	@BeforeEach
	void init() {
		Settings.getInstance().setInstrumentSetting(InstrumentSetting.AUTO);
		Settings.getInstance().setDefaultTuning();
	}

	@AfterEach
	void tearDown() throws Exception {
		Settings.getInstance().setInstrumentSetting(InstrumentSetting.AUTO);
		Settings.getInstance().setDefaultTuning();
	}
	
    @Disabled
    @Test
    void testValidate_validInput1() {
    	Settings.getInstance().setInstrumentSetting(InstrumentSetting.GUITAR);
        List<AnchoredText> lineList = new ArrayList<>();
        lineList.add(new AnchoredText("E|-3-2-2-0----|",0,0));
        lineList.add(new AnchoredText("A|-3-2-2-0----|",0,0));
        lineList.add(new AnchoredText("D|-3-2-2-0----|",0,0));
        lineList.add(new AnchoredText("G|-3-2-2-0----|",0,0));
        lineList.add(new AnchoredText("B|-3-2-2-0----|",0,0));
        lineList.add(new AnchoredText("E|-3-2-2-0----|",0,0));
        TabRow mg = new TabRow(lineList);
        List<ValidationError> errors = mg.validate();
        assertTrue(errors.isEmpty());
    }

//    @Test
//    void testValidate_validInput2() {
//        List<String> origin = new ArrayList<String>();
//        origin.add("[0] E|-3-2-2-0----|");
//        origin.add("[10] A|-3-2-2-0----|");
//        origin.add("[20] D|-3-2-2-0----|");
//        origin.add("[30] G|-3-2-2-0----|");
//        origin.add("[40] B|-3-2-2-0----|");
//        origin.add("[50] e|-3-2-2-0----|");
//        TabRow mg = new TabRow(origin);
//        assertTrue(mg.validate().isEmpty());
//    }
//
//    @Test
//    void testValidate_validInput3() {
//        List<String> origin = new ArrayList<String>();
//        origin.add("[0] E|------------1-|");
//        origin.add("[10] A|-----1-|");
//        origin.add("[20] D|--2-|");
//        origin.add("[30] G|----1-|");
//        origin.add("[40] B|-----1-|");
//        origin.add("[50] e|-----1-|");
//        TabRow mg = new TabRow(origin);
//        assertFalse(mg.validate().isEmpty());
//    }
//
//    @Test
//    void testValidate_validInput4() {
//        List<String> origin = new ArrayList<String>();
//        origin.add("[0] E|------------1|");
//        origin.add("[10] A|-----1|");
//        origin.add("[20] D|--2|");
//        origin.add("[30] G|----1-|");
//        origin.add("[40] B|-----1-|");
//        origin.add("[50] e|-----1-|");
//        TabRow mg = new TabRow(origin);
//        assertFalse(mg.validate().isEmpty());
//    }
//
//    @Test
//    void testValidate_invalidInput2() {
//        List<String> origin = new ArrayList<String>();
//        origin.add("[0] E|------------1-|");
//        origin.add("[10] A|-----1-|");
//        origin.add("[20] abd|--2-|");
//        origin.add("[30] G|----1-|");
//        origin.add("[40] B|-----1-|");
//        origin.add("[50] e|-----1-|");
//        TabRow mg = new TabRow(origin);
//        assertFalse(mg.validate().isEmpty());
//    }
//
//    @Test
//    void testValidate_invalidInput3() {
//        List<String> origin = new ArrayList<String>();
//        origin.add("[0] e|-3-2-2-0---a-|");
//        origin.add("[1] a|-3-2-2-0---b-|");
//        origin.add("[2] d|-3-2-2-0--c--|");
//        origin.add("[3] g|-3-2-2-0---d-|");
//        origin.add("[4] b|-3-2-2-0---e-|");
//        origin.add("[5] e|-3-2-2-0---f-|");
//        TabRow mg = new TabRow(origin);
//        assertFalse(mg.validate().isEmpty());
//    }
//
//    @Test
//    void testToXML() {
//        List<String> origin = new ArrayList<String>();
//        origin.add("[0] e|-3-2-2-0-----0--0-0-----------------------------------------------|");
//        origin.add("[10] B|-3-3-3-0-----0--1-0-----------------------------------------------|");
//        origin.add("[20] G|-4-2-4-2-----0--0-1-----------------------------------------------|");
//        origin.add("[30] D|-5-0-4-2-----2--2-2-----------------------------------------------|");
//        origin.add("[40] A|-5---2-0-----2--3-2-----------------------------------------------|");
//        origin.add("[50] E|-3-----------0----0-----------------------------------------------|");
//        TabRow mg = new TabRow(origin);
//        //assertEquals(mg.toXML(), "");
//    }
//
//    @BeforeEach
//    void init() {
//        List<String> lines = Arrays.asList(
//                "[10]E |--------------------||-------------------|{-------------3-----|---------------8---|---------5---------}|-------------------|",
//                "[20]B |---0-----0---0------||---0-----3---6-----|{-------------------|-------------------|-------------------}|-----3---------2---|",
//                "[40]G |-------0-------0----||-------0-------0---|{----4--------------|-------------------|----3--------------}|-------------------|",
//                "[60]D |--------------------||-------------------|{-------------9-----|-------------------|-------------------}|-----------6-------|",
//                "[80]A |--------------------||-------------------|{-------------------|--------4----------|---------------8---}|-------------------|",
//                "[97]E |---3-------3--------||---3-------3-------|{-------------------|----3--------------|--------5----------}|-----------1-------|"
//        );
//        this.measureGroupInstance = new TabRow(lines);
//    }
//
//    @Test
//    void measureCountValidation() {
//        assertEquals(6, measureGroupInstance.tabMeasures.size());
//    }
//
//    @Test
//    void positions() {
//        List<Integer> expected = Arrays.asList(10,20,40,60,80,97);
//        assertTrue(expected.equals(this.measureGroupInstance.positions));
//    }
}
