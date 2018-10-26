/**
 * Created by chunxiaoli on 5/10/17.
 */
const uuidv4 = require('uuid/v4');
const clientId=uuidv4();
class User {
     static getClientId(){
         return clientId;
     }
}
export default User;


