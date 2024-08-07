package cn.infinitumstudios.infinitumEconomy.classes;

public class Permission extends net.milkbowl.vault.permission.Permission {

    /**
     * @return
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean hasSuperPermsCompat() {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @deprecated
     */
    @Override
    public boolean playerHas(String s, String s1, String s2) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @deprecated
     */
    @Override
    public boolean playerAdd(String s, String s1, String s2) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @deprecated
     */
    @Override
    public boolean playerRemove(String s, String s1, String s2) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @return
     */
    @Override
    public boolean groupHas(String s, String s1, String s2) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @return
     */
    @Override
    public boolean groupAdd(String s, String s1, String s2) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @return
     */
    @Override
    public boolean groupRemove(String s, String s1, String s2) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @deprecated
     */
    @Override
    public boolean playerInGroup(String s, String s1, String s2) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @deprecated
     */
    @Override
    public boolean playerAddGroup(String s, String s1, String s2) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @deprecated
     */
    @Override
    public boolean playerRemoveGroup(String s, String s1, String s2) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public String[] getPlayerGroups(String s, String s1) {
        return new String[0];
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public String getPrimaryGroup(String s, String s1) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String[] getGroups() {
        return new String[0];
    }

    /**
     * @return
     */
    @Override
    public boolean hasGroupSupport() {
        return false;
    }
}
