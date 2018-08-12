package com.future.transaction;

import com.future.transaction.mapper.RecordMapper;
import com.future.transaction.parser.InputParser;
import com.future.transaction.summary.ProcessTransactionRecord;
import com.future.transaction.summary.ReportGenerator;
import com.future.transaction.writer.ProductReportWriter;
import com.future.transaction.writer.SummaryReportWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ReportGeneratorTest {

    private ReportGenerator reportGenerator;

    @Mock
    private RecordMapper recordMapper;
    @Mock
    private ProcessTransactionRecord processTransactionRecord;
    @Mock
    private InputParser inputParser;
    @Mock
    private SummaryReportWriter summaryWriter;
    @Mock
    private ProductReportWriter productReportWriter;

    @Before
    public void setUp() {

        reportGenerator = new ReportGenerator(recordMapper, processTransactionRecord,
                inputParser, summaryWriter, productReportWriter);
        ReflectionTestUtils.setField(reportGenerator, "inputfilePath", "D:/futuretransaction/Input.txt");
    }

    @Test
    public void shouldGenerateReport() {
        reportGenerator.generate();
    }

}
