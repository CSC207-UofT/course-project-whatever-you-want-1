package jsonreadwriter;
import user.UserList;

import java.io.*;

public class UserReadWriter implements ReadWriter {

    /**
     * Writes the users to file at filePath.
     *
     * @param filePath the file to write the records to
     * @param o    stores the list of users to be serialized
     * @throws IOException
     */
    @Override
    public void saveToFile(String filePath, Object o) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(o);
        output.close();
    }


    /**
     * Store the users to file at filePath.
     *
     * @param filePath file where the user list is stored
     * @return list of users
     */
    @Override
    public UserList readFromFile(String filePath) throws IOException, ClassNotFoundException {

        InputStream file = new FileInputStream(filePath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        // serialize the Map
        UserList userslist = (UserList) input.readObject();
        input.close();
        return userslist;
    }
}

//
//public class UserReadWriter implements ReaderWriter{
//
//    private Map<String, UserInfo> libraryOfUsers;
//    private String filePath;
//
//    public UserReadWriter(UserManager userManager) {
//        this.libraryOfUsers = userManager.getMapOfUserInfo();
//        this.filePath = "src/JSON/LibraryOfUsersUsers.main.json";
//    }
//
//
//    @Override
//    public void saveToFile() throws IOException {
//        OutputStream file = new FileOutputStream(filePath);
//        OutputStream buffer = new BufferedOutputStream(file);
//        ObjectOutput output = new ObjectOutputStream(buffer);
//
//        // serialize the Map
//        output.writeObject(libraryOfUsers.toString());
//        output.close();
//    }
//
//    @Override
//    public Map<String, UserInfo> readFromFile() throws IOException, ClassNotFoundException {
//        InputStream file = new FileInputStream(filePath);
//        InputStream buffer = new BufferedInputStream(file);
//        ObjectInput input = new ObjectInputStream(buffer);
//
//        // deserialize the Map
//        Map<String, UserInfo> result = (HashMap<String, UserInfo>) input.readObject();
//        input.close();
//        return result;
//    }
//
//}