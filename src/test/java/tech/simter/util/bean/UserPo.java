package tech.simter.util.bean;

public class UserPo {
  public Integer id;
  public Location location;
  private String account;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }
}