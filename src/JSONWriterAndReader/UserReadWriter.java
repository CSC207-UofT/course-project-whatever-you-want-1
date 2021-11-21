package JSONWriterAndReader;
import User.UserInfo;
import User.UserManager;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserReadWriter implements ReaderWriter{

    private Map<String, UserInfo> libraryOfUsers;
    private String filePath;

    public UserReadWriter(UserManager userManager) {
        this.libraryOfUsers = userManager.getMapOfUserInfo();
        this.filePath = "src/JSON/LibraryOfUsersUsers.json";
    }


    @Override
    public void saveToFile() throws IOException {
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(libraryOfUsers.toString());
        output.close();
    }

    @Override
    public Map<String, UserInfo> readFromFile() throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(filePath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        // deserialize the Map
        HashMap<String, UserInfo> result = (HashMap<String, UserInfo>) input.readObject();
        input.close();
        return result;
    }

}