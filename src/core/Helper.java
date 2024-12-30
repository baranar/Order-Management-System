package core;
import javax.swing.*;

public class Helper {
    //Set Theme
    public static void setTheme(){
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if (info.getName().equals("Nimbus")){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    //Email and Password Control
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fields){
        for (JTextField field: fields){
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static boolean isEmailValid(String mail){
        if (mail == null ||mail.trim().isEmpty()) return false;
        if (!mail.contains("@")) return false;
        String[] parts = mail.split("@");
        if (parts.length !=2) return false;
        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) return false;
        if (!parts[1].contains("."))    return false;
        return true;
    }

    //Dialog panel to "Are You sure?" question
    public static boolean isConfirm(String str){
        optionPaneDialogTranslateToTR();
        String message;

        if (str.equals("sure?")){
            message = "Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
        } else {
            message = str;
        }
        return JOptionPane.showConfirmDialog(null,message,"Emin Misin?", JOptionPane.YES_NO_OPTION) == 0;
    }

    //Dialog panel translate to Turkish
    public static void optionPaneDialogTranslateToTR(){
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }

    //Show message in the Dialog Panel
    public static void showMessage(String message){
        String msg;
        String title;

        switch (message){
            case "fill":
                msg = "Lütfen Tüm Alanları Doldurunuz!";
                title = "HATA!";
                break;
            case "done":
                msg = "İşlem Başarılı!";
                title = "Sonuç";
                break;
            case "error":
                msg = "Bir hata oluştu!";
                title = "HATA";
                break;
            default:
                // Custom message
                msg = message;
                title = "Mesaj";
        }
        JOptionPane.showMessageDialog(null, msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
}
