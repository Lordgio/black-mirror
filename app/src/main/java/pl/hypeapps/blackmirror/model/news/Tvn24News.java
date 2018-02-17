package pl.hypeapps.blackmirror.model.news;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Model reprezentujący kanał wiadomości Tvn24News.
 */
@Root(name = "rss", strict = false)
public class Tvn24News {

    @Element(name = "channel")
    public Channel channel;

    @Attribute(required = false)
    private Double version;
}
