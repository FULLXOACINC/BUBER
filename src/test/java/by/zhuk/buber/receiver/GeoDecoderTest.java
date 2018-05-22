package by.zhuk.buber.receiver;

import by.zhuk.buber.model.Coordinate;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;


public class GeoDecoderTest {

    @Test
    public void decodeCoordinatePositiveTest() throws Exception {
        GeoDecoder decoder = new GeoDecoder();
        Optional<Coordinate> optionalCoordinate = decoder.decodeCoordinate("ул 50 лет победы 23");
        if (optionalCoordinate.isPresent()) {
            Coordinate coordinate = optionalCoordinate.get();
            Assert.assertEquals(coordinate.getLat(), 53.9490, 0.0001);
        }
    }

    @Test
    public void decodeCoordinateEmptyTest() throws Exception {
        GeoDecoder decoder = new GeoDecoder();
        Optional<Coordinate> optionalCoordinate = decoder.decodeCoordinate("ул fff50 лет43пfsdобеды 2fdsssssssss3");
        if (optionalCoordinate.isPresent()) {
            Assert.fail("Mast be empty result");
        }
    }
}