package cn.edu.zju.sishi.message.UserMessage;

/**
 * @author lemon
 * @date 2021/3/18
 */
public class ScoreResponse {

  private String userName;

  private String score;

  private int rank;

  private String avatar;

  private String partyBranch;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getPartyBranch() {
    return partyBranch;
  }

  public void setPartyBranch(String partyBranch) {
    this.partyBranch = partyBranch;
  }
}
