package com.github.cristianocordeiro07.gra.model.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@SuppressWarnings("unused")
public class ProducerAwardsIntervalDTO {

    public ProducerAwardsIntervalDTO(List<ProducerAwardsIntervalObjectDTO> min, List<ProducerAwardsIntervalObjectDTO> max){
        this.min = min;
        this.max = max;
    }

    private List<ProducerAwardsIntervalObjectDTO> min;

    private List<ProducerAwardsIntervalObjectDTO> max;

    public List<ProducerAwardsIntervalObjectDTO> getMin() {
        return min;
    }

    public void setMin(List<ProducerAwardsIntervalObjectDTO> min) {
        this.min = min;
    }

    public List<ProducerAwardsIntervalObjectDTO> getMax() {
        return max;
    }

    public void setMax(List<ProducerAwardsIntervalObjectDTO> max) {
        this.max = max;
    }

    public interface ProducerAwardsIntervalObjectDTO {

        String getProducer();

        @SuppressWarnings("SpringElInspection")
        @Value("#{target.interval_}")
        int getInterval();

        int getPreviousWin();

        int getFollowingWin();
    }
}
