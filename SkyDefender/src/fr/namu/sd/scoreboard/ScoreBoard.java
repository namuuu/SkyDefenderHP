package fr.namu.sd.scoreboard;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreBoard {
    private final MainSD main;

    private String title = "§7• §3SKYDEFENDER §7•";

    public ScoreBoard(MainSD main) {
        this.main = main;
    }

    public void updateBoard() {
        for (FastBoard board : this.main.boards.values()) {
            if(this.main.info.isState(StateSD.GAME)) {
                GameBoard(board);
            } else if (this.main.info.isState(StateSD.LOBBY)) {
                LobbyBoard(board);
            } else if (this.main.info.isState(StateSD.END)){
                EndBoard(board);
            }
        }
    }

    private void GameBoard(FastBoard board) {
        Player player = board.getPlayer();
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());

        String[] score = {
                "§7§m----------------------",
                "§fVous êtes " + psd.getTeam().getName(),
                "§fTimer: §3" + this.main.timer.timerConversed(),
                "",
                "§fAttaquants: §3" + this.main.team.attackersNumber(),
                "§fDéfenseurs: §3" + TeamSD.DEFENSE.getPlayers().size(),
                "",
                "§fDistance au Château: §3" + Math.round(player.getLocation().distance(this.main.info.getBanner())) + "§fm",
                "",
                "§fVous avez: §3" + psd.getKills() + " §fkills",
                "§7Évènement par H.Party",
                "§7§m----------------------"
        };

        if (this.main.spec.getSpecs().contains(player)) {
            score[9] = "§8Vous êtes §7Spectateur §8de la Partie !";
        }

        for (int i = 0; i < score.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(score[i]);
            if (sb.length() > 30)
                sb.delete(29, sb.length() - 1);
            score[i] = sb.toString();
        }

        board.updateTitle(title);
        board.updateLines(score);
    }

    private void LobbyBoard(FastBoard board) {
        String[] score = {
                "§7§m----------------------",
                "Joueurs: §3" + this.main.info.getPlayerSize() + "§7/§3" + this.main.info.getPlayersMax(),
                "§fHost: §3",
                "",
                "§fMap: §3" + this.main.info.getMapName(),
                "",
                "§7Évènement par H.Party",
                "§7§m----------------------"
        };
        if(this.main.info.hostUID != null) {
            score[2] = score[2] + Bukkit.getPlayer(this.main.info.hostUID).getName();
        } else {
            score[2] = "§fMode : §3Partie Classique";
        }

        for (int i = 0; i < score.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(score[i]);
            if (sb.length() > 30)
                sb.delete(29, sb.length() - 1);
            score[i] = sb.toString();
        }

        board.updateTitle(title);
        board.updateLines(score);
    }

    private void EndBoard(FastBoard board) {
        Player player = board.getPlayer();
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());

        String[] score = {
                "",
                "§c  Merci d'avoir joué !",
                "",
                "§7Vous êtes " + psd.getTeam().getName(),
                "§bVous avez : §e" + psd.getKills() + " kills",
                "",
                "",
                "",
                "",
                "",
                "§7Évènement par H.Party"
        };

        for (int i = 0; i < score.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(score[i]);
            if (sb.length() > 30)
                sb.delete(29, sb.length() - 1);
            score[i] = sb.toString();
        }

        board.updateTitle(title);
        board.updateLines(score);
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
