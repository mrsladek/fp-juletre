package no.nav.foreldrepenger.jul;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Kalender {

    private static final Set<String> team = Set.of("AP", "AD", "ALL", "AGA", "BBS", "CH", "EW", "GR", "JEJ", "JOL", "KQ", "KJ", "MLW", "MJS", "PE", "SR", "SHP", "TR");
    private static final Set<String> winners = Set.of("AD", "AGA", "EW", "GR", "JOL", "KQ", "KJ", "MLW", "PE", "TR");

    @GetMapping("/winner")
    ResponseEntity<String> getWinner() {
        String[] waitingToWin = team.stream().filter(it -> !winners.contains(it)).distinct().toArray(String[]::new);
        Random random = new Random();
        var winner = waitingToWin[random.nextInt(team.size() - winners.size())];
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).cacheControl(CacheControl.noCache())
                .body(printTree() + LocalDate.now().toString() + System.getProperty("line.separator") + winner + " du vant! Gratulerer!");
    }

    private static StringBuffer printTree() {
        StringBuffer buffer = new StringBuffer();
        printTriangle(buffer, 10, 0, 10);
        printTriangle(buffer, 5, 5, 15);
        printTriangle(buffer, 0, 10, 20);
        printSquare(buffer, 15, 9, 4);
        return buffer;
    }

    private static void printTriangle(StringBuffer buffer, int indent, int start, int size) {
        for (int row = start; row < size; row++)
            buffer.append(" ".repeat(size - row - 1 + indent)).append("*".repeat(1 + 2 * row)).append(System.getProperty("line.separator"));
    }

    private static void printSquare(StringBuffer buffer, int indent, int width, int height) {
        for (int row = 0; row < height; row++)
            buffer.append(" ".repeat(indent)).append("*".repeat(width)).append(System.getProperty("line.separator"));
    }

}
