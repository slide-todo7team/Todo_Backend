package com.example.todo_Backend.group.service;

import com.example.todo_Backend.User.Member.Repository.MemberRepository;
import com.example.todo_Backend.User.Member.entity.Member;
import com.example.todo_Backend.group.dto.*;
import com.example.todo_Backend.group.entity.Group;
import com.example.todo_Backend.group.entity.GroupUser;
import com.example.todo_Backend.group.exception.DuplicationGroupTitleException;
import com.example.todo_Backend.group.exception.NotEqualSecretCodeException;
import com.example.todo_Backend.group.exception.NotFoundGroupException;
import com.example.todo_Backend.group.repository.GroupRepository;
import com.example.todo_Backend.group.repository.GroupUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupUserRepository groupUserRepository;

    public GroupService(GroupRepository groupRepository, MemberRepository memberRepository, GroupUserRepository groupUserRepository) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.groupUserRepository = groupUserRepository;
    }

    //그룹 생성
    public GroupCreateResponseDto createGroup(GroupCreateRequestDto groupCreateRequestDto) {

        Integer secretCode = generateRandomNumber();
        groupCreateRequestDto.setSecretCode(String.valueOf(secretCode));

        Group group = new Group(groupCreateRequestDto);
        String title = groupCreateRequestDto.getTitle();

        //그룹 이름 중복 확인
        if(groupRepository.findByTitle(title).isPresent()){
            throw new DuplicationGroupTitleException(groupCreateRequestDto.getTitle());
        }

        Group saveGroup = groupRepository.save(group); //group 테이블에 정보 저장
        saveGroupMemInfo(saveGroup.getId(),groupCreateRequestDto.getMemberId(),1,ColorEnum.getRandomHex());

        return new GroupCreateResponseDto(saveGroup);
    }

    //그룹 참여
    public GroupCreateResponseDto enterGroup(String secretCode, Long memberId) {

        //초대코드 존재 확인
        Group group = groupRepository.findBySecretCode(secretCode)
                .orElseThrow(() -> new NotEqualSecretCodeException(secretCode));

        saveGroupMemInfo(group.getId(), memberId,0,ColorEnum.getRandomHex());

        return new GroupCreateResponseDto(group);
    }

    //참여중인 그룹 리스트 조회
    public List<GroupDto> getGroupsByMemberId(Long memberId) {
        List<GroupUser> groupUsers = groupUserRepository.findByMemberId(memberId);
        return groupUsers.stream()
                .map(gu -> new GroupDto(gu.getGroup().getGroupId(), gu.getGroup().getTitle()))
                .collect(Collectors.toList());
    }

    //그룹 상세 조회
    public GroupInfoDto getGroupInfo(Long groupId){

        //그룹 entity 조회
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundGroupException(groupId));

        //group_user entity 조회
        List<GroupUser> groupUsers = groupUserRepository.findByGroupId(groupId);

        //member entity 조회
        List<Member> members = memberRepository.findByMemIdIn(groupUsers.stream()
                .map(GroupUser::getMemberId)
                .collect(Collectors.toList()));

        //DTO 매핑
        GroupInfoDto groupInfoDto = new GroupInfoDto(group);

        List<GroupInfoDto.GroupMemberDto> groupMemberDtos = members.stream()
                .map(member -> {
                    GroupUser groupUser = groupUsers.stream()
                            .filter(gu -> gu.getMemberId().equals(member.getMemId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("GroupUser not found"));

                    return GroupInfoDto.GroupMemberDto.builder()
                            .id(member.getMemId())
                            .isLeader(groupUser.getIsLeader())
                            .name(member.getName())
                            .color(groupUser.getColor())
                            .contributionRank(groupUser.getContributionRank())
                            .build();
                })
                .collect(Collectors.toList());

        // groupInfoDto에 groupMemberDtos 설정
        groupInfoDto.setMembers(groupMemberDtos);

        return groupInfoDto;
    }

    //그룹 삭제 (방장)
    @Transactional
    public GroupResponse deleteGroup(Long groupId){

        //그룹 존재 확인
        if(groupRepository.findById(groupId).isEmpty()){
            throw new NotFoundGroupException(groupId);
        }

        //그룹 테이블, group_user 테이블에서 삭제
        groupUserRepository.deleteByGroupId(groupId);
        groupRepository.deleteById(groupId);

        return GroupResponse.ok();
    }

    //그룹 탈퇴
    @Transactional
    public GroupResponse leaveGroup(Long groupId, Long memberId){

        //그룹 존재 확인
        if(groupRepository.findById(groupId).isEmpty()){
            throw new NotFoundGroupException(groupId);
        }

        groupUserRepository.deleteByGroupIdAndMemberId(groupId,memberId);

        return GroupResponse.ok();
    }

    public GroupCodeDto getNewSecretCode(Long groupId){

        //그룹 존재 확인
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundGroupException(groupId));

        //새 코드 생성
        int newCode = generateRandomNumber();

        return GroupCodeDto.builder()
                .secretCode(String.valueOf(newCode))
                .build();

    }

    public GroupInfoDto updateSecretcode(Long groupId, String newSecretCode){
        //그룹 존재 확인
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundGroupException(groupId));

        group.setSecretCode(Integer.parseInt(newSecretCode));
        groupRepository.save(group);

        return getGroupInfo(groupId);

    }


    //그룹 멤버 저장
    public void saveGroupMemInfo(Long groupId, Long memberId, Integer isLeader,String Color) {
        GroupUser groupUser = new GroupUser();
        groupUser.setGroup(groupRepository.getReferenceById(groupId));
        groupUser.setMemberId(memberId);
        groupUser.setIsLeader(isLeader);
        groupUser.setColor(Color);
        groupUserRepository.save(groupUser);
    }

    //6자리 랜덤 숫자 생성
    public int generateRandomNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
