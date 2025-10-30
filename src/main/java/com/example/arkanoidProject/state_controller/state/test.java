//////// ball - wall
////////if (ball.getHitBox().getMinX() <= 0) {
////////ball.setDx(Math.abs(ball.getDx())); // bật sang phải
////////}
////////if (ball.getHitBox().getMaxX() >= WIDTH) {
////////ball.setDx(-Math.abs(ball.getDx())); // bật sang trái
////////}
////////if (ball.getHitBox().getMinY() <= 0) {
////////ball.setDy(Math.abs(ball.getDy())); // bật xuống
////////}
////////if (ball.getHitBox().getMaxY() >= HEIGHT) {
////////ball.setDy(-Math.abs(ball.getDy())); // bật lên
////////}
////////if (ball.getHitBox().getMinX() <= 0) ball.setDx(- ball.getDx());
////////if (ball.getHitBox().getMaxX() >= HEIGHT) ball.setDx(- ball.getDx());
////////if (ball.getHitBox().getMinY() <= 0) ball.setDy(- ball.getDy());
////////if (ball.getHitBox().getMaxY() >= HEIGHT) ball.setDy(- ball.getDy());
//////
//////import javafx.geometry.Rectangle2D;if (ball.getHitBox().intersects(paddle.getHitBox())) {
//////    double ballCenterX = ball.getHitBox().getMinX() + ball.getHitBox().getWidth() / 2;
//////    double ballCenterY = ball.getHitBox().getMinY() + ball.getHitBox().getHeight / 2;
//////    double paddleCenterX = paddle.getHitBox().getMinX() + paddle.getHitBox().getWidth() / 2;
//////    double paddleCenterY = paddle.getHitBox().getMinY() + paddle.getHitBox().getHeight() / 2;
//////
//////    ball.setDx(ballCenterX - paddleCenterX);
//////    ball.setDy(ballCenterY - paddleCenterY);
//////}
//////// ======== BALL - PADDLE COLLISION ========
//////Rectangle2D ballBox = ball.getHitBox();
//////Rectangle2D paddleBox = paddle.getHitBox();
//////        if (ballBox.intersects(paddleBox)) {
//////// 🔹 Tâm của paddle và bóng
//////double paddleCenter = paddleBox.getMinX() + paddleBox.getWidth() / 2;
//////double ballCenter = ballBox.getMinX() + ballBox.getWidth() / 2;
//////
//////// 🔹 Khoảng cách tương đối từ tâm bóng đến tâm paddle
//////double relativeIntersect = (ballCenter - paddleCenter) / (paddleBox.getWidth() / 2);
//////relativeIntersect = Math.max(-1, Math.min(1, relativeIntersect)); // tránh vượt biên
//////
//////// 🔹 Giới hạn góc bật từ -60° → +60°
//////double maxAngle = Math.toRadians(60);
//////double bounceAngle = relativeIntersect * maxAngle;
//////
//////// 🔹 Tốc độ hiện tại
//////double speed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());
//////
//////// 🔹 Cập nhật vận tốc mới
//////            ball.setDx(speed * Math.sin(bounceAngle));
//////        ball.setDy(-Math.abs(speed * Math.cos(bounceAngle))); // luôn bật lên
//////
//////        // 🔹 Đặt bóng ngay trên paddle (tránh dính)
//////        ball.setY(paddleBox.getMinY() - ballBox.getHeight() - 0.5);
//////        }
////
////if (ball.getHitBox().getMinX() <= 0) {
////    ball.setX(ball.getX() - ball.getHitBox().getMinX());
////    ball.setDx(- ball.getDx());
////}
////
////
////
////// Biên phải
////        if (ball.getHitBox().getMaxX() >= WIDTH) {
////        ball.setX(ball.getX() - (ball.getHitBox().getMaxX() - WIDTH));
////        ball.setDx(-ball.getDx());
////        }
////
////// Biên trên
////        if (ball.getHitBox().getMinY() <= 0) {
////        ball.setY(ball.getY() - ball.getHitBox().getMinY());
////        ball.setDy(-ball.getDy());
////        }
////
////// Biên dưới
////        if (ball.getHitBox().getMaxY() >= HEIGHT) {
////        ball.setY(ball.getY() - (ball.getHitBox().getMaxY() - HEIGHT));
////        ball.setDy(-ball.getDy());
////        }
//
//import com.example.arkanoidProject.object.Brick;
//import javafx.geometry.Rectangle2D;
//    for (Brick brick : bricks) {
//    if (brick.isDestroy()) continue;
//    Rectangle2D brickHitBox = new Rectangle2D(
//            brick.getX(),
//            brick.getY(),
//            brick.getWidth(),
//            brick.getHeight()
//    );
//
//    if (ball.getHitBox().intersects(brickHitBox)) {
//        double ballCenterX = ball.getHitBox().getMinX() + ball.getHitBox().getWidth() / 2;
//        double ballCenterY = ball.getHitBox().getMinY() + ball.getHitBox().getHeight() / 2;
//        double brickCenterX = brick.getX() + brick.getWidth() / 2;
//        double brickCenterY = brick.getY() + brick.getHeight() / 2;
//
//        // Hệ số 10 - 30 dùng để tăng tốc ball.
//        // 10 < 30 vì muốn bóng đi theo chiều dọc nhanh hơn chều ngang.
//        ball.setDx((ballCenterX - brickCenterX) * 10);
//        ball.setDy((ballCenterY - brickCenterY) * 30);
//
//        brick.destroy();
//    }
//}
//Rectangle2D ballBox = ball.getHitBox();
//Rectangle2D paddleBox = paddle.getHitBox();
//        for (
//Brick brick : bricks) {
//        if (brick.isDestroyed()) continue;
//
//double bx1 = ballBox.getMinX();
//double by1 = ballBox.getMinY();
//double bx2 = ballBox.getMaxX();
//double by2 = ballBox.getMaxY();
//
//double rx1 = brick.getX();
//double ry1 = brick.getY();
//double rx2 = brick.getX() + brick.getWidth();
//double ry2 = brick.getY() + brick.getHeight();
//
//// Kiểm tra giao nhau
//            if (bx2 > rx1 && bx1 < rx2 && by2 > ry1 && by1 < ry2) {
//
//// Tính khoảng cách giữa tâm để xác định hướng bật
//double ballCenterX = (bx1 + bx2) / 2;
//double ballCenterY = (by1 + by2) / 2;
//double brickCenterX = (rx1 + rx2) / 2;
//double brickCenterY = (ry1 + ry2) / 2;
//
//double dx = ballCenterX - brickCenterX;
//double dy = ballCenterY - brickCenterY;
//
//// Xác định hướng va chạm đơn giản và chắc chắn
//                if (Math.abs(dx) > Math.abs(dy)) {
//        // Bật ngang
//        if (dx > 0) {
//        ball.setX(rx2 + 1); // Đẩy ra phải
//                    } else {
//                            ball.setX(rx1 - ballBox.getWidth() - 1); // Đẩy ra trái
//        }
//        ball.setDx(-ball.getDx());
//        } else {
//        // Bật dọc
//        if (dy > 0) {
//        ball.setY(ry2 + 1); // Đẩy ra dưới
//                    } else {
//                            ball.setY(ry1 - ballBox.getHeight() - 1); // Đẩy ra trên
//        }
//        ball.setDy(-ball.getDy());
//        }
//
//        brick.destroy();
//                break; // chỉ phá 1 brick mỗi frame
//                        }
//                        }