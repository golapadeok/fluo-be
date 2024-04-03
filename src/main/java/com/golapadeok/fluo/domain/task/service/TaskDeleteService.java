package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.task.domain.ManagerTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.response.TaskDeleteResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.ManagerTaskRepository;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskDeleteService {
    private final TaskRepository taskRepository;
    private final ManagerTaskRepository managerTaskRepository;

    @Transactional
    public TaskDeleteResponse delete(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(NotFoundTaskException::new);

        //업무와 관련된 데이터 삭제
//        List<ManagerTask> managerTasks = managerTaskRepository.findAllByTaskId(taskId);
//        managerTasks.forEach(manager -> {
//            task.getManagers().remove(manager);
//            manager.changeTask(null);
//            manager.changeMember(null);
//            managerTaskRepository.delete(manager);
//        });

//        task.getWorkspace().getTasks().remove(task);
//        task.changeWorkspace(null);
        taskRepository.deleteById(taskId);
        return new TaskDeleteResponse("삭제에 성공했습니다.");
    }
}
//java.lang.NullPointerException: Cannot invoke "com.golapadeok.fluo.domain.workspace.domain.Workspace.getTasks()" because "this.workspace" is null