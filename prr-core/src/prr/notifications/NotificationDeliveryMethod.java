package prr.notifications;

import java.io.Serializable;

public interface NotificationDeliveryMethod extends Serializable {
  void deliver(Notification notification);
}
