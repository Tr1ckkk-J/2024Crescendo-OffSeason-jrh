package frc.robot.commands.AutoCommand;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.IntakeCommands.NoteIntake;
import frc.robot.commands.SPKCommands.NewAutoSPKUP;
import frc.robot.commands.SPKCommands.TestSPKUP;
import frc.robot.subsystems.Chassis.DriveSubsystem;

public class C10X extends SequentialCommandGroup {
        
    int FirstP,SecondP;
    public C10X(int _FirstP,int _SecondP)
    {
        FirstP=_FirstP;
        SecondP=_SecondP;
        if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue) {
            addCommands(new InstantCommand(() -> RobotContainer.m_Swerve
                    .setPose(RobotContainer.m_Swerve.generateChoreoPath("C103-1").getPreviewStartingHolonomicPose())));

        } else {
            Pose2d m_Pose = RobotContainer.m_Swerve.generateChoreoPath("C103-1").flipPath()
                    .getPreviewStartingHolonomicPose();
            Pose2d flipm_Pose = new Pose2d(m_Pose.getX(), m_Pose.getY(),
                    m_Pose.getRotation().plus(new Rotation2d(Math.PI)));
            addCommands(new InstantCommand(() -> RobotContainer.m_Swerve.setPose(flipm_Pose)));

        }
        addCommands(RobotContainer.m_Swerve.followPathCommand(RobotContainer.m_Swerve.generateChoreoPath("C103-1")));
        addCommands(new NewAutoSPKUP(0));
        if(FirstP!=0){
            addCommands(RobotContainer.m_Swerve.followPathCommand(RobotContainer.m_Swerve.generateChoreoPath("C103-P"+Integer.toString(FirstP)))
                    .raceWith(new NoteIntake(0)));
            addCommands(new NewAutoSPKUP(0).withTimeout(1.5));
        }
        if(SecondP!=0){
            addCommands(RobotContainer.m_Swerve.followPathCommand(RobotContainer.m_Swerve.generateChoreoPath("C103-P"+Integer.toString(SecondP)))
                    .raceWith(new NoteIntake(0)));
            addCommands(new NewAutoSPKUP(0).withTimeout(1.5));
        }
    }
}
