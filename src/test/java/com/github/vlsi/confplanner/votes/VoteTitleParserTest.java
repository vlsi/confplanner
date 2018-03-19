package com.github.vlsi.confplanner.votes;

import com.github.vlsi.confplanner.model.Language;
import com.github.vlsi.confplanner.model.Speaker;
import com.github.vlsi.confplanner.model.Talk;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class VoteTitleParserTest {
    @Test
    public void testSingleSpeaker() throws Exception {
        VoteTitleParser p = new VoteTitleParser();
        Talk talk = p.parse("От клика к прогнозу и обратно: Data Science пайплайны в ОК — Дмитрий Бугайченко, Одноклассники [300/RU]");
        Assert.assertEquals(talk.getLanguage(), new Language("ru"));
        Assert.assertEquals(talk.getName(), "От клика к прогнозу и обратно: Data Science пайплайны в ОК");
        Assert.assertEquals(talk.getSpeakers(), Arrays.asList(new Speaker("Дмитрий Бугайченко")));
    }

    @Test
    public void testSingleSpeaker2() throws Exception {
        VoteTitleParser p = new VoteTitleParser();
        Talk talk = p.parse("Глубокое обучение, вероятностное программирование и метавычисления: точка пересечения — Алексей Потапов, Университет ИТМО [400/RU]");
        Assert.assertEquals(talk.getLanguage(), new Language("ru"));
        Assert.assertEquals(talk.getName(), "Глубокое обучение, вероятностное программирование и метавычисления: точка пересечения");
        Assert.assertEquals(talk.getSpeakers(), Arrays.asList(new Speaker("Алексей Потапов")));
    }

    @Test
    public void testSingleSpeaker3() throws Exception {
        VoteTitleParser p = new VoteTitleParser();
        Talk talk = p.parse("«Жизнь без подключения: от хаоса к консенсусу» - Евгений Камышанов, EPAM Systems [300/RU]");
        Assert.assertEquals(talk.getLanguage(), new Language("ru"));
        Assert.assertEquals(talk.getName(), "Жизнь без подключения: от хаоса к консенсусу");
        Assert.assertEquals(talk.getSpeakers(), Arrays.asList(new Speaker("Евгений Камышанов")));
    }

    @Test
    public void testMultiSpeaker() throws Exception {
        VoteTitleParser p = new VoteTitleParser();
        Talk talk = p.parse("Troubleshooting & debugging production applications in Kubernetes (a.k.a. The Failing Demo Talk) — Ray Тsang, Baruch Sadogursky, Google, JFrog  [200/EN]");
        Assert.assertEquals(talk.getLanguage(), new Language("en"));
        Assert.assertEquals(talk.getName(), "Troubleshooting & debugging production applications in Kubernetes (a.k.a. The Failing Demo Talk)");
        Assert.assertEquals(talk.getSpeakers(), Arrays.asList(new Speaker("Ray Тsang"), new Speaker("Baruch Sadogursky")));
    }

    @Test
    public void testKeynote() throws Exception {
        VoteTitleParser p = new VoteTitleParser();
        Talk talk = p.parse("Кейноут: Unchain my heart —Dino Esposito  [200/ENG]");
        Assert.assertEquals(talk.getLanguage(), new Language("en"));
        Assert.assertEquals(talk.getName(), "Кейноут: Unchain my heart");
        Assert.assertEquals(talk.getSpeakers(), Arrays.asList(new Speaker("Dino Esposito")));
    }

    @Test
    public void testComment() throws Exception {
        VoteTitleParser p = new VoteTitleParser();
        Talk talk = p.parse("The (Ab)use and misuse of test automation — Alan Page, Unity [300/EN](автор книги про тесты в Microsoft)");
        Assert.assertEquals(talk.getLanguage(), new Language("en"));
        Assert.assertEquals(talk.getName(), "The (Ab)use and misuse of test automation");
        Assert.assertEquals(talk.getSpeakers(), Arrays.asList(new Speaker("Alan Page")));
    }
}
