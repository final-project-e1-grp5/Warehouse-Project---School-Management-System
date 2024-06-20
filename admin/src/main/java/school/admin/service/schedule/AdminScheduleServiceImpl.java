package school.admin.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.admin.model.dto.schedule.AddScheduleDto;
import school.admin.model.dto.schedule.ScheduleDto;
import school.admin.proxy.ScheduleProxy;
import java.util.List;

@Service
public class AdminScheduleServiceImpl implements AdminScheduleService {

    @Autowired
    private ScheduleProxy scheduleProxy;

    @Override
    public ScheduleDto addSchedule(AddScheduleDto addScheduleDto) {
        return scheduleProxy.addSchedule(addScheduleDto);
    }

    @Override
    public ScheduleDto getSchedule(Long id) {
        return scheduleProxy.getSchedule(id);
    }


    @Override
    public List<ScheduleDto> getSchedulesByClass(Long classId) {
        return scheduleProxy.getSchedulesByClass(classId);
    }

    @Override
    public List<ScheduleDto> getSchedulesByTeacher(String teacherId) {
        return scheduleProxy.getSchedulesByTeacher(teacherId);
    }

    @Override
    public ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto) {
        return scheduleProxy.updateSchedule(id, scheduleDto);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleProxy.deleteSchedule(id);
    }
}
