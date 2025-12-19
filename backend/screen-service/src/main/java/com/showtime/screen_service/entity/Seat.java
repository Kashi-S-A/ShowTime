package  com.showtime.screen_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
	    uniqueConstraints = @UniqueConstraint(
	        columnNames = {"screen_id", "row_label", "seat_number"}
	    )
	)
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer seat_id;
	
	@Column(name = "seat_number", nullable = false)
	Integer seatNumber;

	@Column(name = "row_label", nullable = false)
	String rowLabel;

	
	@ManyToOne
	@JoinColumn(name = "category_id")
	Category category;
	
	@ManyToOne
	@JoinColumn(name = "screen_id")
	Screen screen;
	
	Boolean is_blocked=false;
	
	@Enumerated(EnumType.STRING)
	Theatre_Status status;
	
	
	
}
