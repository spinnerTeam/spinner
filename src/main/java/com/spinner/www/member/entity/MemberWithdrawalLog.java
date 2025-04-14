package com.spinner.www.member.entity;

import com.spinner.www.member.constants.WithdrawalReason;
import com.spinner.www.member.io.WithdrawMemberIo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "member_withdrawal_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Comment("탈퇴사유 테이블")
public class MemberWithdrawalLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("탈퇴사유 PK")
    private Long memberWithdrawalLogIdx;

    @Comment("탈퇴 회원번호")
    private Long memberWithdrawalLogMemberIdx;

    @Comment("탈퇴사유")
    @Enumerated(EnumType.STRING)
    private WithdrawalReason withdrawalReason;

    @Comment("탈퇴 직접 입력")
    private String memberWithdrawalLogReason;

    public static MemberWithdrawalLog insertMemberWithdrawalLog(WithdrawMemberIo withdrawMemberIo, Long memberIdx){
        return MemberWithdrawalLog.builder()
                .memberWithdrawalLogMemberIdx(memberIdx)
                .withdrawalReason(WithdrawalReason.valueOf(withdrawMemberIo.getWithdrawalReason()))
                .memberWithdrawalLogReason(withdrawMemberIo.getMemberWithdrawalLogReason())
                .build();
    }
}
