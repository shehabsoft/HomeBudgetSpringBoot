package com.homeBudget.jobs;


import com.homeBudget.dao.NotificationsDAO;
import com.homeBudget.model.Notifications;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class UpdateRecovedPateintsJob implements Job {





    private final Logger log = LoggerFactory.getLogger(UpdateRecovedPateintsJob.class);

     @Autowired
     private NotificationsDAO notificationsDAO;

    @Autowired
    private JavaMailSender javaMailSender;

    public UpdateRecovedPateintsJob( ) {
        System.out.println("Begin UpdateRecovedPateintsJob");
    }

    @Override
    public void execute(JobExecutionContext context) {
        log.debug("context = [" + context.getJobRunTime() + "]");

        List<Notifications> notificationsList=  notificationsDAO.findByStatus(1);
    //  sendEmailWithInlineImage();
        for(int i=0;i<notificationsList.size();i++)
        {
            log.debug(" Sent Notification to  "+notificationsList.get(i).getMail_to());
            System.out.println(" Sent Notification to  "+notificationsList.get(i).getMail_to());
          //  sendEmailWithInlineImage();
            try {
                Notifications notifications=notificationsList.get(i);
                Boolean sent=sendEmaiL(notificationsList.get(i));
                if(sent)
                {
                   sendEmaiL(notificationsList.get(i));
                }
                notifications.setStatus(2);
                notificationsDAO.save(notifications);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
           System.out.println("End Of UpdateRecovedPateintsJob");

        log.debug(" End Of UpdateRecovedPateintsJob ");
    }

    Boolean sendEmaiL(Notifications notifications) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(notifications.getMail_to());
        helper.setCc("shehabsoft94@gmail.com");
        helper.setSubject("Order Confirmation");
        helper.setText(notifications.getNotification_content(), true);

        //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));

        //Resource resource = new ClassPathResource("android.png");
        //InputStream input = resource.getInputStream();

        //ResourceUtils.getFile("classpath:android.png");

        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);
        return true;
    }

    public void sendEmailWithInlineImage() {
        MimeMessage mimeMessage = null;
        try {
            InternetAddress from = new InternetAddress("shehabsoft94@gmail.com", "shehabsoft94@gmail.com");
            mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setSubject("Test Inline");
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo("shehabsoft94@gmail.com");
            String contentId = ContentIdGenerator.getContentId();
            String texttt="<a href=\"https://smart-services.herokuapp.com/products/product/[ITEM_ID]\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:14px;text-decoration:underline;color:#D48344\"><img src=\\\"cid:\" + contentId + \"\\\" alt=\"Sheep Bakri\" class=\"adapt-img\" title=\"Sheep Bakri\" width=\"125\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a>";
   //         String htmlText = "Hello,</br> <p>This is test with email inlines.</p><img src=\"cid:" + contentId + "\" />";
            helper.setText(texttt, true);

            ClassPathResource classPathResource = new ClassPathResource("android.png");
            helper.addInline(contentId, classPathResource);




            javaMailSender.send(mimeMessage);
        }
        catch (Exception e) {
            //LOGGER.error(e.getMessage());
        }

    }
    public void convertImage(byte [] bytes )
    {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");

            //ImageIO is a class containing static methods for locating ImageReaders
            //and ImageWriters, and performing simple encoding and decoding.

            ImageReader reader = (ImageReader) readers.next();
            Object source = bis;
            ImageInputStream iis = ImageIO.createImageInputStream(source);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();

            Image image = reader.read(0, param);
            //got an image file

            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            //bufferedImage is the RenderedImage to be written

            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(image, null, null);
            ClassPathResource classPathResource = new ClassPathResource("android.png");
            File imageFile = new File(classPathResource.getURI());
            ImageIO.write(bufferedImage, "png", imageFile);

            System.out.println(imageFile.getPath());
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    }
